package br.com.fsferreira.productapi.entities.product.service;

import br.com.fsferreira.productapi.config.exception.GenericUserException;
import br.com.fsferreira.productapi.config.validators.TypeValidator;
import br.com.fsferreira.productapi.entities.category.model.Category;
import br.com.fsferreira.productapi.entities.category.service.CategoryService;
import br.com.fsferreira.productapi.entities.product.dto.ProductRequestInput;
import br.com.fsferreira.productapi.entities.product.dto.ProductResponseOutput;
import br.com.fsferreira.productapi.entities.product.model.Product;
import br.com.fsferreira.productapi.entities.product.repository.ProductRepository;
import br.com.fsferreira.productapi.entities.supplier.service.SupplierService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;

    public List<ProductResponseOutput> findAll() {
        return repository.findAll().stream().map(ProductResponseOutput::of).toList();
    }

    @Transactional
    public ProductResponseOutput save(ProductRequestInput input){
        validateProduct(input);
        var category = categoryService.findById(input.getCategoryId());
        var supplier = supplierService.findById(input.getSupplierId());

        var product = repository.save(Product.of(input,category,supplier));
        return ProductResponseOutput.of(product);
    }

    private void validateProduct(ProductRequestInput input) {
        if(input.getName() == null || input.getName().isEmpty()){
            throw new GenericUserException("Product's name not informed!");
        }
        if(input.getAmount() == null || input.getAmount() < 0){
            throw new GenericUserException("Product's amount not valid!");
        }
        if(input.getCategoryId() == null || input.getCategoryId().isEmpty()){
            throw new GenericUserException("Category's id not informed!");
        }
        if(input.getSupplierId() == null || input.getSupplierId().isEmpty()){
            throw new GenericUserException("Supplier's id not informed!");
        }
        TypeValidator.validateUUID(input.getSupplierId(), "Supplier's id inválid!");
        TypeValidator.validateUUID(input.getCategoryId(), "Category's id inválid!");
    }


}
