package br.com.fsferreira.productapi.entities.supplier.dto;

import java.util.Objects;

public class SupplierRequestInput {
    private String name;

    public SupplierRequestInput() {
    }

    public SupplierRequestInput(String name) {
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
        SupplierRequestInput that = (SupplierRequestInput) o;
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
