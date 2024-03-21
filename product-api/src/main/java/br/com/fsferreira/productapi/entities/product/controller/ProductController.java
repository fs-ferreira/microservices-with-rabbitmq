package br.com.fsferreira.productapi.entities.product.controller;

import br.com.fsferreira.productapi.entities.category.dto.CategoryRequestInput;
import br.com.fsferreira.productapi.entities.category.dto.CategoryResponseOutput;
import br.com.fsferreira.productapi.entities.category.service.CategoryService;
import br.com.fsferreira.productapi.entities.product.dto.ProductRequestInput;
import br.com.fsferreira.productapi.entities.product.dto.ProductResponseOutput;
import br.com.fsferreira.productapi.entities.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping
    public List<ProductResponseOutput> findAll() {
        return service.findAll();
    }
    @PostMapping
    public ProductResponseOutput save(@RequestBody ProductRequestInput input) {
        return service.save(input);
    }

}
