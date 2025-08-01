package com.sena.BusinessAssistantSpring.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SaleDetailId implements Serializable {

    private Integer saleId;
    private Integer lotId;

    public SaleDetailId() {}

    public SaleDetailId(Integer saleId, Integer lotId) {
        this.saleId = saleId;
        this.lotId = lotId;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Integer getLotId() {
        return lotId;
    }

    public void setLotId(Integer lotId) {
        this.lotId = lotId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaleDetailId)) return false;
        SaleDetailId that = (SaleDetailId) o;
        return Objects.equals(saleId, that.saleId) &&
               Objects.equals(lotId, that.lotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, lotId);
    }
}
