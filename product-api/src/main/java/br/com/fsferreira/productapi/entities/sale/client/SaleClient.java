package br.com.fsferreira.productapi.entities.sale.client;


import br.com.fsferreira.productapi.entities.sale.dto.SaleProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "saleClient",
        contextId = "saleClient",
        url = "${app-config.services.sale}"
)
public interface SaleClient {

    @GetMapping("/api/order/product/{productId}")
    Optional<SaleProductResponse> findSalesByProductId(@PathVariable String productId);
}
