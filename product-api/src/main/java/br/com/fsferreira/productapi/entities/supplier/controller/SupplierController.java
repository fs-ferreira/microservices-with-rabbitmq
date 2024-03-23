package br.com.fsferreira.productapi.entities.supplier.controller;

import br.com.fsferreira.productapi.entities.supplier.dto.SupplierRequestInput;
import br.com.fsferreira.productapi.entities.supplier.dto.SupplierResponseOutput;
import br.com.fsferreira.productapi.entities.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    SupplierService service;

    @GetMapping
    public List<SupplierResponseOutput> findAll() {
        return service.findAll();
    }

    @PostMapping
    public SupplierResponseOutput save(@RequestBody SupplierRequestInput input) {
        return service.save(input);
    }

    @PutMapping("/{id}")
    public SupplierResponseOutput update(@PathVariable(name = "id") String id, @RequestBody SupplierRequestInput input) {
        return service.update(id, input);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") String id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
