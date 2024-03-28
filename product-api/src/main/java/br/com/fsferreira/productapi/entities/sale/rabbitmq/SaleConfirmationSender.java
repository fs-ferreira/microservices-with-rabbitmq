package br.com.fsferreira.productapi.entities.sale.rabbitmq;

import br.com.fsferreira.productapi.entities.sale.dto.SaleConfirmationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SaleConfirmationSender {

    Logger logger = LoggerFactory.getLogger(SaleConfirmationSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app-config.rabbit.exchange.product}")
    private String productTopicExchange;

    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;

    public void sendSaleConfirmation(SaleConfirmationDTO message) {
        try {
            logger.info("Sending message: {}", new ObjectMapper().writeValueAsString(message));
            rabbitTemplate.convertAndSend(productTopicExchange, salesConfirmationKey, message);
            logger.info("Message successfully sent!");
        } catch (Exception ex) {
            logger.info("Error on send message: ", ex);
        }
    }


}
