package br.com.fsferreira.productapi.entities.category.service;

import br.com.fsferreira.productapi.config.exception.GenericNotFoundException;
import br.com.fsferreira.productapi.config.exception.GenericUserException;
import br.com.fsferreira.productapi.entities.category.dto.CategoryRequestInput;
import br.com.fsferreira.productapi.entities.category.dto.CategoryResponseOutput;
import br.com.fsferreira.productapi.entities.category.model.Category;
import br.com.fsferreira.productapi.entities.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<CategoryResponseOutput> findAll() {
        return repository.findAll().stream().map(CategoryResponseOutput::of).toList();
    }

    public CategoryResponseOutput findById(String id) {
        var uuid = validateCategoryId(id);
        var category = repository.findById(uuid)
                .orElseThrow(() ->new GenericNotFoundException("Category not found!"));
        return CategoryResponseOutput.of(category);
    }

    @Transactional
    public CategoryResponseOutput save(CategoryRequestInput input) {
        validateCategoryName(input);
        var category = repository.save(Category.of(input));
        return CategoryResponseOutput.of(category);
    }

    private void validateCategoryName(CategoryRequestInput input) {
        if (input.getName() == null || input.getName().isEmpty()) {
            throw new GenericUserException("Category's name not informed!");
        }
    }

    private UUID validateCategoryId(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException exception) {
            throw new GenericUserException("ID is not valid!");
        }
    }

}
