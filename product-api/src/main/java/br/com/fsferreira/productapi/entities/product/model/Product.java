package br.com.fsferreira.productapi.entities.product.model;

import br.com.fsferreira.productapi.entities.category.dto.CategoryRequestInput;
import br.com.fsferreira.productapi.entities.category.dto.CategoryResponseOutput;
import br.com.fsferreira.productapi.entities.category.model.Category;
import br.com.fsferreira.productapi.entities.product.dto.ProductRequestInput;
import br.com.fsferreira.productapi.entities.supplier.dto.SupplierResponseOutput;
import br.com.fsferreira.productapi.entities.supplier.model.Supplier;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "fk_category", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "fk_supplier", nullable = false)
    private Supplier supplier;
    @Column(name = "amount", columnDefinition = "integer default 0")
    private Integer amount;

    public Product() {
    }

    public Product(ProductBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.category = builder.category;
        this.supplier = builder.supplier;
        this.amount = builder.amount;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(category, product.category) && Objects.equals(supplier, product.supplier) && Objects.equals(amount, product.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, supplier, amount);
    }
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", supplier=" + supplier +
                ", amount=" + amount +
                '}';
    }

    public static Product of(ProductRequestInput product,
                             CategoryResponseOutput category,
                             SupplierResponseOutput supplier) {
        return new ProductBuilder(product.getId(), product.getName(), product.getAmount())
                .setCategory(category)
                .setSupplier(supplier)
                .build();
    }

    public static class ProductBuilder{
        private final UUID id;
        private final String name;
        private Category category;
        private Supplier supplier;
        private final Integer amount;

        public ProductBuilder(UUID id, String name, Integer amount) {
            this.id = id;
            this.name = name;
            this.amount = amount;
        }

        public ProductBuilder setCategory(CategoryResponseOutput category) {
            this.category = new Category();
            BeanUtils.copyProperties(category, this.category);
            return this;
        }

        public ProductBuilder setSupplier(SupplierResponseOutput supplier) {
            this.supplier = new Supplier();
            BeanUtils.copyProperties(supplier, this.supplier);
            return this;
        }
        public Product build(){
            return new Product(this);
        }

    }
}
