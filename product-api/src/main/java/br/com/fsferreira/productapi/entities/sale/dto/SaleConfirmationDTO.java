package br.com.fsferreira.productapi.entities.sale.dto;

import br.com.fsferreira.productapi.entities.sale.enums.SaleStatus;

import java.util.Objects;

public class SaleConfirmationDTO {

    private String saleId;
    private SaleStatus status;

    public SaleConfirmationDTO() {
    }

    public SaleConfirmationDTO(String saleId, SaleStatus status) {
        this.saleId = saleId;
        this.status = status;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public SaleStatus getStatus() {
        return status;
    }

    public void setStatus(SaleStatus status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleConfirmationDTO that = (SaleConfirmationDTO) o;
        return Objects.equals(saleId, that.saleId) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, status);
    }
}
