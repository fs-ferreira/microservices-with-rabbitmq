package br.com.fsferreira.productapi.entities.product.dto;

import br.com.fsferreira.productapi.entities.category.dto.CategoryResponseOutput;
import br.com.fsferreira.productapi.entities.category.model.Category;
import br.com.fsferreira.productapi.entities.product.model.Product;
import br.com.fsferreira.productapi.entities.supplier.dto.SupplierResponseOutput;
import org.springframework.beans.BeanUtils;

import java.util.Objects;
import java.util.UUID;

public class ProductResponseOutput {

    private UUID id;
    private String name;
    private Integer amount;
    private SupplierResponseOutput supplier;
    private CategoryResponseOutput category;

    public ProductResponseOutput() {
    }

    public ProductResponseOutput(UUID id, String name, Integer amount, SupplierResponseOutput supplier, CategoryResponseOutput category) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.supplier = supplier;
        this.category = category;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public SupplierResponseOutput getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierResponseOutput supplier) {
        this.supplier = supplier;
    }

    public CategoryResponseOutput getCategory() {
        return category;
    }

    public void setCategory(CategoryResponseOutput category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductResponseOutput that = (ProductResponseOutput) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(amount, that.amount) && Objects.equals(supplier, that.supplier) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, supplier, category);
    }

    @Override
    public String toString() {
        return "ProductResponseOutput{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", supplier=" + supplier +
                ", category=" + category +
                '}';
    }

    public static ProductResponseOutput of(Product product) {
        var response = new ProductResponseOutput();
        BeanUtils.copyProperties(product, response);
        response.setCategory(CategoryResponseOutput.of(product.getCategory()));
        response.setSupplier(SupplierResponseOutput.of(product.getSupplier()));
        return  response;
    }
}
