import OrderRepository from "../repository/OrderRepository.js";
import { handleSendProductStockUpdate } from "../../Product/rabbitMq/productStockUpdateSender.js";
import * as httpStatus from "../../../config/constants/httpStatus.js"
import { PENDING } from "../../../config/constants/orderEnum.js";
import OrderException from "../exceptions/OrderException.js";

class OrderService {
  async createOrder(req) {
    try {
      const orderData = req.body;
      const { authUser } = req;
      let order = {
        status: PENDING,
        user: authUser,
        createdAt: new Date(),
        updatedAt: new Date(),
        products: orderData
      }
      await validateStock(order)
      let createdOrder = await OrderRepository.save(order)
      handleSendProductStockUpdate(createdOrder.products)
      return {
        status: httpStatus.SUCCESS,
        createdOrder
      }
    } catch (err) {
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message
      }
    }
  }

  validateOrderData(data) {
    if (!data || !data.products) {
      throw new OrderException(httpStatus.BAD_REQUEST, 'At least one product must be informed!')
    }
    if (data.products.find(el => !el.productId || !el.amount)) {
      throw new OrderException(httpStatus.BAD_REQUEST, 'There is an invalid product on payload!')
    }
  }

  async validateStock(order){
    // WIP
    throw new OrderException(httpStatus.BAD_REQUEST, 'The stock is out of products.')
  }

  async updateOrder(orderMessage){
    try {
      const order = JSON.parse(orderMessage)
      this.validateSaleOrder(order)
      let existingOrder = await OrderRepository.findById(order.salesId)
      if(existingOrder && order.status !== existingOrder.status){
        existingOrder.status = order.status;
        await OrderRepository.save(existingOrder)
      }
    } catch (err) {
      console.error(err.message);
      throw new OrderException(httpStatus.INTERNAL_SERVER_ERROR, 'Could not parse order message from queue!')
    }
  }

  validateSaleOrder(order){
    if(!order.status && !order.status){
      throw new OrderException(httpStatus.INTERNAL_SERVER_ERROR, 'Order messas is incomplete!')
    }
  }

}

export default new OrderService();