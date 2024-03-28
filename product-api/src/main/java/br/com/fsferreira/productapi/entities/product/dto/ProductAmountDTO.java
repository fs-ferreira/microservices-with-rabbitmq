package br.com.fsferreira.productapi.entities.product.dto;

import java.util.Objects;
import java.util.UUID;

public class ProductAmountDTO {
    private String productId;
    private Integer amount;

    public ProductAmountDTO() {
    }

    public ProductAmountDTO(String productId, Integer amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAmountDTO that = (ProductAmountDTO) o;
        return Objects.equals(productId, that.productId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, amount);
    }
}
