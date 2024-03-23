package br.com.fsferreira.productapi.entities.category.controller;

import br.com.fsferreira.productapi.entities.category.dto.CategoryRequestInput;
import br.com.fsferreira.productapi.entities.category.dto.CategoryResponseOutput;
import br.com.fsferreira.productapi.entities.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/name")
    public List<CategoryResponseOutput> findByName(@RequestParam String name) {
        return service.findByName(name);
    }

    @PostMapping
    public CategoryResponseOutput save(@RequestBody CategoryRequestInput input) {
        return service.save(input);
    }

    @PutMapping("/{id}")
    public CategoryResponseOutput update(@PathVariable(name = "id") String id, @RequestBody CategoryRequestInput input) {
        return service.update(id, input);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") String id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
