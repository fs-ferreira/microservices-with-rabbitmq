import ampq from 'amqplib/callback_api.js'
import * as rabbitConstants from "./queue.js"
import { RABBIT_MQ_URL } from '../constants/secrets.js'
import { handleListenSaleQueue } from '../../entities/Sales/rabbitMq/saleConfirmationListener.js';

const HALF_SECOND = 500;
const HALF_MINUTE = 30000;
const CONTAINER_ENV = "container"

export async function connectRabbitMq() {
  const env = process.env.NODE_ENV;
  if (CONTAINER_ENV === env) {
    console.info("Waiting for rabbitMQ to start...")
    setInterval(async () => {
      await connectAndCreate()
    }, HALF_MINUTE)
  } else {
    await connectAndCreate()
  }
}

function createQueue(connection, queue, routingKey, topic) {
  connection.createChannel((error, channel) => {
    if (error) {
      throw error
    }
    channel.assertExchange(topic, 'topic', { durable: true });
    channel.assertQueue(queue, { durable: true });
    channel.bindQueue(queue, topic, routingKey)
  })
}

 function connectAndCreate() {
  ampq.connect(RABBIT_MQ_URL, async (error, connection) => {
    if (error) {
      throw error
    }
    await createQueue(connection,
      rabbitConstants.PRODUCT_STOCK_UPDATE_QUEUE,
      rabbitConstants.PRODUCT_STOCK_UPDATE_ROUTING_KEY,
      rabbitConstants.PRODUCT_TOPIC)
    await createQueue(connection,
      rabbitConstants.SALES_CONFIRMATION_QUEUE,
      rabbitConstants.SALES_CONFIRMATION_ROUTING_KEY,
      rabbitConstants.PRODUCT_TOPIC)
    setTimeout(() => {
      connection.close();
    }, HALF_SECOND);
    handleListenSaleQueue();
  });
}
