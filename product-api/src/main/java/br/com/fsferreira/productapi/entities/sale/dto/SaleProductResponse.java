package br.com.fsferreira.productapi.entities.sale.dto;

import java.util.List;
import java.util.Objects;

public class SaleProductResponse {
    private List<String> salesId;


    public SaleProductResponse() {
    }

    public SaleProductResponse(List<String> salesId) {
        this.salesId = salesId;
    }

    public List<String> getSalesId() {
        return salesId;
    }

    public void setSalesId(List<String> salesId) {
        this.salesId = salesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleProductResponse that = (SaleProductResponse) o;
        return Objects.equals(salesId, that.salesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salesId);
    }
}
