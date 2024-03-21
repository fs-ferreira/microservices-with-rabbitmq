package br.com.fsferreira.productapi.entities.category.dto;

import br.com.fsferreira.productapi.entities.category.model.Category;
import org.springframework.beans.BeanUtils;

import java.util.Objects;
import java.util.UUID;

public class CategoryResponseOutput {
    private UUID id;
    private String name;

    public CategoryResponseOutput() {
    }

    public CategoryResponseOutput(UUID id, String name) {
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
        CategoryResponseOutput that = (CategoryResponseOutput) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CategoryResponseOutput{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static CategoryResponseOutput of(Category category) {
        var response = new CategoryResponseOutput();
        BeanUtils.copyProperties(category, response);
        return  response;
    }
}
