package br.com.fsferreira.productapi.entities.supplier.repository;

import br.com.fsferreira.productapi.entities.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
}
