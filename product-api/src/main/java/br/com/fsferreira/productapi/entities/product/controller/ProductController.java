package br.com.fsferreira.productapi.entities.product.controller;

import br.com.fsferreira.productapi.entities.category.dto.CategoryRequestInput;
import br.com.fsferreira.productapi.entities.category.dto.CategoryResponseOutput;
import br.com.fsferreira.productapi.entities.category.service.CategoryService;
import br.com.fsferreira.productapi.entities.product.dto.ProductCheckStockRequest;
import br.com.fsferreira.productapi.entities.product.dto.ProductRequestInput;
import br.com.fsferreira.productapi.entities.product.dto.ProductResponseOutput;
import br.com.fsferreira.productapi.entities.product.dto.ProductSalesResponse;
import br.com.fsferreira.productapi.entities.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("{id}/sales")
    public ProductSalesResponse findProductSales(@PathVariable String id) {
        return service.findProductSales(id);
    }

    @PostMapping
    public ProductResponseOutput save(@RequestBody ProductRequestInput input) {
        return service.save(input);
    }

    @PutMapping("/{id}")
    public ProductResponseOutput update(@PathVariable(name = "id") String id, @RequestBody ProductRequestInput input) {
        return service.update(id, input);
    }

    @PutMapping("/checkStock")
    public ResponseEntity<String> checkStock(@RequestBody ProductCheckStockRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.checkStock(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") String id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
