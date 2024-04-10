import express from 'express'
import { connect } from './src/config/db/dbConfig.js';
import authMiddleware from './src/config/auth/authMiddleware.js';
import { connectRabbitMq } from './src/config/rabbitmq/rabbitConfig.js';
import { handleSendProductStockUpdate } from './src/entities/Product/rabbitMq/productStockUpdateSender.js';

const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

connect();
connectRabbitMq()

app.use(authMiddleware)

app.get('/api/status', async (req, res) => {
  return res.status(200).json({
    service: "Sales-API",
    status: "up",
    httpStatus: 200
  })
})

app.listen(PORT, () => {
  console.info(`Server started at port: ${PORT}`)
})