import { Router } from "express";
import OrderController from "../controller/OrderController.js";

const router = new Router()

router.get('/api/order', OrderController.findAll);
router.get('/api/order/:id', OrderController.findById);
router.get('/api/order/product/:productId', OrderController.findByProductId);
router.post('/api/order/create', OrderController.createOrder);
router.delete('/api/order', OrderController.deleteOrders);

export default router;