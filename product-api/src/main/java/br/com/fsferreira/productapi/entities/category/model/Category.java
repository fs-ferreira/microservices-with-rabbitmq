package br.com.fsferreira.productapi.entities.category.model;

import br.com.fsferreira.productapi.entities.category.dto.CategoryRequestInput;
import br.com.fsferreira.productapi.entities.category.dto.CategoryResponseOutput;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    public Category() {
    }

    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    public static Category of(CategoryRequestInput category) {
        var request = new Category();
        BeanUtils.copyProperties(category, request);
        return  request;
    }

    public static Category of(CategoryResponseOutput category) {
        var request = new Category();
        BeanUtils.copyProperties(category, request);
        return  request;
    }
}
