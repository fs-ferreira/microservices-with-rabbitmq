import Order from "../Order.js";

class OrderRepository {

  async save(order) {
    try {
      return await Order.create(order)
    } catch (err) {
      console.error(err.message);
      return null;
    }
  }

  async findById(id) {
    try {
      return await Order.findById(id)
    } catch (err) {
      console.error(err.message);
      return null;
    }
  }

  async find() {
    try {
      return await Order.find()
    } catch (err) {
      console.error(err.message);
      return null;
    }
  }

  async findByProductId(productId) {
    try {
      return await Order.find({ "products.productId": productId })
    } catch (err) {
      console.error(err.message);
      return null;
    }
  }

  async deleteAll() {
    try {
      return await Order.deleteMany()
    } catch (err) {
      console.error(err.message);
      return null;
    }
  }

}

export default new OrderRepository();