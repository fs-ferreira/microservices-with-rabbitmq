package br.com.fsferreira.productapi.entities.category.repository;

import br.com.fsferreira.productapi.entities.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByNameContainsIgnoreCase(String name);
}
