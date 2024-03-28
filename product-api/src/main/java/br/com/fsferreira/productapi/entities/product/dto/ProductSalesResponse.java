package br.com.fsferreira.productapi.entities.product.dto;

import br.com.fsferreira.productapi.entities.category.dto.CategoryResponseOutput;
import br.com.fsferreira.productapi.entities.product.model.Product;
import br.com.fsferreira.productapi.entities.supplier.dto.SupplierResponseOutput;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ProductSalesResponse extends ProductResponseOutput {

    private List<String> sales;

    public ProductSalesResponse() {
    }

    public ProductSalesResponse(UUID id, String name, Integer amount, SupplierResponseOutput supplier, CategoryResponseOutput category, List<String> sales) {
        super(id, name, amount, supplier, category);
        this.sales = sales;
    }

    public List<String> getSales() {
        return sales;
    }

    public void setSales(List<String> sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductSalesResponse that = (ProductSalesResponse) o;
        return Objects.equals(sales, that.sales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sales);
    }


    public static ProductSalesResponse of(ProductResponseOutput product,
                                          List<String> sales) {
        var response = new ProductSalesResponse();
        BeanUtils.copyProperties(product, response);
        response.setSales(sales);
        response.setCategory(product.getCategory());
        response.setSupplier(product.getSupplier());
        return response;
    }
}
