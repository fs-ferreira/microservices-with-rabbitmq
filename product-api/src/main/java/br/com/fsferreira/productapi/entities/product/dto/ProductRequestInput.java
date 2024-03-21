package br.com.fsferreira.productapi.entities.product.dto;

import java.util.Objects;
import java.util.UUID;

public class ProductRequestInput {
    private UUID id;
    private String name;
    private String supplierId;
    private String categoryId;
    private Integer amount;

    public ProductRequestInput() {
    }

    public ProductRequestInput(UUID id, String name, String supplierId, String categoryId, Integer amount) {
        this.id = id;
        this.name = name;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
        ProductRequestInput that = (ProductRequestInput) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(supplierId, that.supplierId) && Objects.equals(categoryId, that.categoryId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, supplierId, categoryId, amount);
    }
}
