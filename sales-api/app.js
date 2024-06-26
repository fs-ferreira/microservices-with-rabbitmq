import express from 'express'
import { connect } from './src/config/db/dbConfig.js';
import authMiddleware from './src/config/auth/authMiddleware.js';
import { connectRabbitMq } from './src/config/rabbitmq/rabbitConfig.js';

import orderRoutes from './src/entities/Sales/routes/OrderRoutes.js'
import tracing from './src/config/tracing.js';

const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

connect();
connectRabbitMq();

app.use(tracing)
app.use(express.json())
app.use(authMiddleware);
app.use(orderRoutes);

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