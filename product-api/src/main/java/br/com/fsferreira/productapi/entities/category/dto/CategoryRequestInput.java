package br.com.fsferreira.productapi.entities.category.dto;

import java.util.Objects;

public class CategoryRequestInput {
    private String name;

    public CategoryRequestInput() {
    }

    public CategoryRequestInput(String name) {
        this.name = name;
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
        CategoryRequestInput that = (CategoryRequestInput) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "CategoryRequestInput{" +
                "name='" + name + '\'' +
                '}';
    }
}
