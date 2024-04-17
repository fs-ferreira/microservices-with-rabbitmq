import OrderRepository from "../repository/OrderRepository.js";
import { handleSendProductStockUpdate } from "../../Product/rabbitMq/productStockUpdateSender.js";
import * as httpStatus from "../../../config/constants/httpStatus.js"
import { PENDING } from "../../../config/constants/orderEnum.js";
import OrderException from "../exceptions/OrderException.js";
import ProductClient from "../../Product/client/ProductClient.js";

class OrderService {
  async createOrder(req) {
    try {
      const orderData = req.body;
      this.validateOrderData(orderData)
      const { authUser } = req;
      const { authorization } = req.headers;
      const order = this.setupOrder(authUser, orderData)
      await this.validateStock(order, authorization)
      const createdOrder = await OrderRepository.save(order)
      this.sendMessage(createdOrder)
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

  setupOrder(authUser, orderData) {
    return {
      status: PENDING,
      user: authUser,
      createdAt: new Date(),
      updatedAt: new Date(),
      products: orderData.products
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

  async validateStock(order, token) {
    console.warn(order);
    const stockIsValid = await ProductClient.checkProductStock(order.products, token)
    if (!stockIsValid) {
      throw new OrderException(httpStatus.BAD_REQUEST, 'The stock is out of products.')
    }
  }

  sendMessage(order) {
    const message = {
      saleId: order.id,
      products: order.products
    }
    handleSendProductStockUpdate(message)
  }

  async updateOrder(orderMessage) {
    try {
      const order = JSON.parse(orderMessage)
      this.validateSaleOrder(order)
      let existingOrder = await OrderRepository.findById(order.saleId)
      if (existingOrder && order.status !== existingOrder.status) {
        existingOrder.status = order.status;
        existingOrder.updatedAt = new Date();
        await OrderRepository.save(existingOrder)
      }
    } catch (err) {
      console.error(err.message);
      throw new OrderException(httpStatus.INTERNAL_SERVER_ERROR, 'Could not parse order message from queue!')
    }
  }

  validateSaleOrder(order) {
    if (!order.status && !order.status) {
      throw new OrderException(httpStatus.INTERNAL_SERVER_ERROR, 'Order message is incomplete!')
    }
  }

  async findAll() {
    try {
      const orders = await OrderRepository.find();
      if (!orders) {
        throw new OrderException(httpStatus.NOT_FOUND, 'Orders not found!')
      }
      return {
        status: httpStatus.SUCCESS,
        orders
      }
    } catch (err) {
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message
      }
    }
  }

  async findById(req) {
    try {
      const { id } = req.params;
      this.validateId(id)
      const existingOrder = await OrderRepository.findById(id);
      if (!existingOrder) {
        throw new OrderException(httpStatus.NOT_FOUND, 'Order not found!')
      }
      return {
        status: httpStatus.SUCCESS,
        existingOrder
      }
    } catch (err) {
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message
      }
    }
  }

  async findByProductId(req) {
    try {
      const { productId } = req.params;
      this.validateProductId(productId)
      const orders = await OrderRepository.findByProductId(productId);
      if (!orders) {
        throw new OrderException(httpStatus.NOT_FOUND, 'Orders not found!')
      }
      return {
        status: httpStatus.SUCCESS,
        salesId: orders.map(order => order.id)
      }
    } catch (err) {
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message
      }
    }
  }

  validateId(id) {
    if (!id) {
      throw new OrderException(httpStatus.BAD_REQUEST, 'Order ID must be informed!')
    }
  }

  validateProductId(id) {
    if (!id) {
      throw new OrderException(httpStatus.BAD_REQUEST, 'Product ID must be informed!')
    }
  }

  async deleteOrders() {
    try {
     await OrderRepository.deleteAll();
      return {
        status: httpStatus.NO_CONTENT,
        message: "Orders successfully deleted!"
      }
    } catch (err) {
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message
      }
    }
  }

}

export default new OrderService();