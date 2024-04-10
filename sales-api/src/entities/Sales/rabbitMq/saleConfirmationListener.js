import ampq from 'amqplib/callback_api.js'
import { RABBIT_MQ_URL } from '../../../config/constants/secrets.js'
import { SALES_CONFIRMATION_QUEUE } from '../../../config/rabbitmq/queue.js';
import OrderService from '../service/OrderService.js';

export function handleListenSaleQueue() {
  ampq.connect(RABBIT_MQ_URL, (error, connection) => {
    if (error) {
      throw error
    }
    connection.createChannel((error, channel) => {
      if (error) {
        throw error
      }
      channel.consume(SALES_CONFIRMATION_QUEUE, (message) => {
        console.info(message.content.toString())
        OrderService.updateOrder(message)
      }, { noAck: true })
    })
  });

}