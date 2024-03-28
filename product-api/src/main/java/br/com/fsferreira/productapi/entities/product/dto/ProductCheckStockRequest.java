package br.com.fsferreira.productapi.entities.product.dto;

import java.util.List;
import java.util.Objects;

public class ProductCheckStockRequest {

    List<ProductAmountDTO> products;

    public ProductCheckStockRequest() {
    }
    public ProductCheckStockRequest(List<ProductAmountDTO> products) {
        this.products = products;
    }

    public List<ProductAmountDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductAmountDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCheckStockRequest that = (ProductCheckStockRequest) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }
}
