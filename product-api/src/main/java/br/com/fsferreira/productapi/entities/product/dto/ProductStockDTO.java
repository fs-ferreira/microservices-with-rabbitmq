package br.com.fsferreira.productapi.entities.product.dto;

import java.util.List;
import java.util.Objects;

public class ProductStockDTO {
    private String saleId;
    private List<ProductAmountDTO> products;

    public ProductStockDTO() {
    }

    public ProductStockDTO(String saleId, List<ProductAmountDTO> products) {
        this.saleId = saleId;
        this.products = products;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public List<ProductAmountDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductAmountDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductStockDTO that = (ProductStockDTO) o;
        return Objects.equals(saleId, that.saleId) && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, products);
    }
}
