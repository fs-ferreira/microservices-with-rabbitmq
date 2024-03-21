package br.com.fsferreira.productapi.entities.category.controller;

import br.com.fsferreira.productapi.entities.category.dto.CategoryRequestInput;
import br.com.fsferreira.productapi.entities.category.dto.CategoryResponseOutput;
import br.com.fsferreira.productapi.entities.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping
    public List<CategoryResponseOutput> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponseOutput findById(@PathVariable(name = "id") String id) {
        return service.findById(id);
    }

    @PostMapping
    public CategoryResponseOutput save(@RequestBody CategoryRequestInput input) {
        return service.save(input);
    }

}
