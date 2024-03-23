package br.com.fsferreira.productapi.entities.category.service;

import br.com.fsferreira.productapi.config.exception.GenericNotFoundException;
import br.com.fsferreira.productapi.config.exception.GenericServerException;
import br.com.fsferreira.productapi.config.exception.GenericUserException;
import br.com.fsferreira.productapi.entities.category.dto.CategoryRequestInput;
import br.com.fsferreira.productapi.entities.category.dto.CategoryResponseOutput;
import br.com.fsferreira.productapi.entities.category.model.Category;
import br.com.fsferreira.productapi.entities.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
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
                .orElseThrow(() -> new GenericNotFoundException("Category not found!"));
        return CategoryResponseOutput.of(category);
    }

    public List<CategoryResponseOutput> findByName(String name) {
        var list = repository.findByNameContainsIgnoreCase(name);
        validateCategoryList(list);
        return list.stream().map(CategoryResponseOutput::of).toList();
    }

    @Transactional
    public CategoryResponseOutput save(CategoryRequestInput input) {
        validateCategoryName(input);
        var category = repository.save(Category.of(input));
        return CategoryResponseOutput.of(category);
    }

    @Transactional
    public CategoryResponseOutput update(String id, CategoryRequestInput input) {
        validateCategoryName(input);
        var category = findById(id);
        BeanUtils.copyProperties(input, category);
        var response = repository.save(Category.of(category));
        return CategoryResponseOutput.of(response);
    }


    @Transactional
    public void delete(String id) {
        try {
            var category = findById(id);
            repository.delete(Category.of(category));
        } catch (IllegalArgumentException exception) {
            throw new GenericServerException("Fail to delete the category!");
        }
    }


    private void validateCategoryName(CategoryRequestInput input) {
        if (input.getName() == null || input.getName().isEmpty()) {
            throw new GenericUserException("Category's name not informed!");
        }
    }

    private void validateCategoryList(List<Category> list) {
        if (list.isEmpty()) {
            throw new GenericNotFoundException("No records found!");
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
