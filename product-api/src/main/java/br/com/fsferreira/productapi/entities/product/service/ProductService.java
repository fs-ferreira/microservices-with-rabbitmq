package br.com.fsferreira.productapi.entities.product.service;

import br.com.fsferreira.productapi.config.exception.GenericNotFoundException;
import br.com.fsferreira.productapi.config.exception.GenericServerException;
import br.com.fsferreira.productapi.config.exception.GenericUserException;
import br.com.fsferreira.productapi.config.validator.TypeValidator;
import br.com.fsferreira.productapi.entities.category.service.CategoryService;
import br.com.fsferreira.productapi.entities.product.dto.*;
import br.com.fsferreira.productapi.entities.product.model.Product;
import br.com.fsferreira.productapi.entities.product.repository.ProductRepository;
import br.com.fsferreira.productapi.entities.sale.client.SaleClient;
import br.com.fsferreira.productapi.entities.sale.dto.SaleConfirmationDTO;
import br.com.fsferreira.productapi.entities.sale.enums.SaleStatus;
import br.com.fsferreira.productapi.entities.sale.rabbitmq.SaleConfirmationSender;
import br.com.fsferreira.productapi.entities.supplier.service.SupplierService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductService {

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    SaleConfirmationSender saleConfirmationSender;
    @Autowired
    SaleClient saleClient;

    public List<ProductResponseOutput> findAll() {
        return repository.findAll().stream().map(ProductResponseOutput::of).toList();
    }

    public ProductResponseOutput findById(String id) {
        var uuid = TypeValidator.validateUUID(id, "Id is not valid!");
        var product = repository.findById(uuid)
                .orElseThrow(() -> new GenericNotFoundException("Product not found!"));
        return ProductResponseOutput.of(product);
    }


    @Transactional
    public ProductResponseOutput save(ProductRequestInput input) {
        validateProduct(input);
        var category = categoryService.findById(input.getCategoryId());
        var supplier = supplierService.findById(input.getSupplierId());

        var product = repository.save(Product.of(input, category, supplier));
        return ProductResponseOutput.of(product);
    }

    @Transactional
    public ProductResponseOutput update(String id, ProductRequestInput input) {
        validateProduct(input);
        var product = findById(id);
        BeanUtils.copyProperties(input, product, "id");
        var category = categoryService.findById(input.getCategoryId());
        var supplier = supplierService.findById(input.getSupplierId());

        return ProductResponseOutput.of(repository.save(Product.of(product, category, supplier)));
    }

    @Transactional
    public void delete(String id) {
        try {
            var product = findById(id);
            repository.deleteById(product.getId());
        } catch (IllegalArgumentException exception) {
            throw new GenericServerException("Fail to delete the product!");
        }
    }

    private void validateProduct(@NonNull ProductRequestInput input) {
        if (input.getName() == null || input.getName().isEmpty()) {
            throw new GenericUserException("Product's name not informed!");
        }
        if (input.getAmount() == null || input.getAmount() < 0) {
            throw new GenericUserException("Product's amount not valid!");
        }
        if (input.getCategoryId() == null || input.getCategoryId().isEmpty()) {
            throw new GenericUserException("Category's id not informed!");
        }
        if (input.getSupplierId() == null || input.getSupplierId().isEmpty()) {
            throw new GenericUserException("Supplier's id not informed!");
        }
        TypeValidator.validateUUID(input.getSupplierId(), "Supplier's id inválid!");
        TypeValidator.validateUUID(input.getCategoryId(), "Category's id inválid!");
    }

    public void updateProductStock(ProductStockDTO product) {
        try {
            validateStockUpdate(product);
            handleUpdateProductStock(product);
        } catch (Exception ex) {
            logger.error("Error on update stock: {}", ex.getMessage(), ex);
            var rejectedMessage = new SaleConfirmationDTO(product.getSaleId(), SaleStatus.REJECTED);
            saleConfirmationSender.sendSaleConfirmation(rejectedMessage);
        }
    }

    @Transactional
    private void handleUpdateProductStock(ProductStockDTO product) {
        var productsForPersist = new ArrayList<Product>();
        product.getProducts()
                .forEach(productAmount -> {
                    var inBaseProduct = findById(productAmount.getProductId());
                    validateStockInBase(productAmount, inBaseProduct);
                    var persistProduct = Product.of(inBaseProduct, inBaseProduct.getCategory(), inBaseProduct.getSupplier());
                    persistProduct.updateStock(productAmount.getAmount());
                    productsForPersist.add(persistProduct);
                });
        if (!productsForPersist.isEmpty()) {
            repository.saveAll(productsForPersist);
            var approvedMessage = new SaleConfirmationDTO(product.getSaleId(), SaleStatus.APPROVED);
            saleConfirmationSender.sendSaleConfirmation(approvedMessage);
        }
    }

    private void validateStockUpdate(ProductStockDTO product) {
        if (isEmpty(product) || isEmpty(product.getSaleId())) {
            throw new GenericUserException("Payload and saleId cannot be null!");
        }
        if (product.getProducts().isEmpty()) {
            throw new GenericUserException("Sale must contain at least one product!");
        }

        product.getProducts().forEach(productAmount -> {
            if (isEmpty(productAmount.getAmount()) || isEmpty(productAmount.getProductId())) {
                throw new GenericUserException("Product must contain an ID and amount!");
            }
        });
    }

    private void validateStockInBase(ProductAmountDTO productAmount, ProductResponseOutput inBaseProduct) {
        if (productAmount.getAmount() > inBaseProduct.getAmount()) {
            throw new GenericUserException(
                    String.format("The product %s is out of stock", inBaseProduct.getName()));
        }
    }

    public ProductSalesResponse findProductSales(@PathVariable String id) {
        var product = findById(id);
        try {
            var sales = saleClient.findSalesByProductId(product.getId().toString())
                    .orElseThrow(() -> new GenericNotFoundException("No sales found for this product!"));
            return ProductSalesResponse.of(product, sales.getSalesId());
        } catch (Exception ex) {
            throw new GenericServerException("There was an error trying to get product's sales!");
        }
    }

    public String checkStock(ProductCheckStockRequest request) {
        if (isEmpty(request) || isEmpty(request.getProducts())) {
            throw new GenericUserException("Request payload must be valid!");
        }
        request.getProducts().forEach(this::validateStock);
        return "The stock is valid!";
    }

    private void validateStock(ProductAmountDTO productAmount) {
        if (isEmpty(productAmount.getProductId()) || isEmpty(productAmount.getAmount())) {
            throw new GenericUserException("Product payload must contain ID and amount");
        }
        var product = findById(productAmount.getProductId());
        if (product.getAmount() < productAmount.getAmount()) {
            throw new GenericUserException(
                    String.format("The product %s is out of stock", product.getName()));
        }
    }
}
