package br.com.fsferreira.productapi.entities.supplier.model;

import br.com.fsferreira.productapi.entities.supplier.dto.SupplierRequestInput;
import br.com.fsferreira.productapi.entities.supplier.dto.SupplierResponseOutput;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;

    public Supplier() {
    }

    public Supplier(UUID id, String name) {
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
        Supplier supplier = (Supplier) o;
        return Objects.equals(id, supplier.id) && Objects.equals(name, supplier.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    public static Supplier of(SupplierRequestInput input) {
        var request = new Supplier();
        BeanUtils.copyProperties(input, request);
        return  request;
    }

    public static Supplier of(SupplierResponseOutput input) {
        var request = new Supplier();
        BeanUtils.copyProperties(input, request);
        return  request;
    }
}
