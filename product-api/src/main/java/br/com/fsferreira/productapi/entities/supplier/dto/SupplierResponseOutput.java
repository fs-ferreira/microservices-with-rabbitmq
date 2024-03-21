package br.com.fsferreira.productapi.entities.supplier.dto;

import br.com.fsferreira.productapi.entities.supplier.model.Supplier;
import org.springframework.beans.BeanUtils;

import java.util.Objects;
import java.util.UUID;

public class SupplierResponseOutput {
    private UUID id;
    private String name;

    public SupplierResponseOutput() {
    }

    public SupplierResponseOutput(UUID id, String name) {
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
        SupplierResponseOutput that = (SupplierResponseOutput) o;
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

    public static SupplierResponseOutput of(Supplier supplier) {
        var response = new SupplierResponseOutput();
        BeanUtils.copyProperties(supplier, response);
        return  response;
    }
}
