package br.com.fsferreira.productapi.entities.product.rabbitmq;

import br.com.fsferreira.productapi.entities.product.dto.ProductStockDTO;
import br.com.fsferreira.productapi.entities.product.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductStockListener {

    @Autowired
    ProductService service;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductStock(ProductStockDTO product) {
        service.updateProductStock(product);
    }

}
