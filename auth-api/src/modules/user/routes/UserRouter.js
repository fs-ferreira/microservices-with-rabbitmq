import { Router } from "express";
import UserController from "../controller/UserController.js";
import authMiddleware from "../../../config/auth/authMiddleware.js";


const router = new Router();

router.post('/api/user/auth', UserController.getAccessToken)

router.use(authMiddleware)

router.get('/api/user/email/:email', UserController.findByEmail)

export default router;