import ampq from 'amqplib/callback_api.js'
import { RABBIT_MQ_URL } from '../../../config/constants/secrets.js'
import { PRODUCT_STOCK_UPDATE_ROUTING_KEY, PRODUCT_TOPIC } from '../../../config/rabbitmq/queue.js';

export function handleSendProductStockUpdate(message) {
  ampq.connect(RABBIT_MQ_URL, (error, connection) => {
    if (error) {
      throw error
    }
    connection.createChannel((error, channel) => {
      if (error) {
        throw error
      }
      let jsonMessage = JSON.stringify(message)
      console.info("Sending message...", message)
      channel.publish(
        PRODUCT_TOPIC,
        PRODUCT_STOCK_UPDATE_ROUTING_KEY,
        Buffer.from(jsonMessage)
      )
    });
  });

}