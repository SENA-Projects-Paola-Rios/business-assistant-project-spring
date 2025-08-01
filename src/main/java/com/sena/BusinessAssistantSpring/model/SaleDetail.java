package com.sena.BusinessAssistantSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "sale_detail")
public class SaleDetail {

    @EmbeddedId
    private SaleDetailId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("saleId")
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("lotId")
    @JoinColumn(name = "lot_id", nullable = false)
    private Lot lot;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public SaleDetail() {}

    public SaleDetail(Sale sale, Lot lot, Integer quantity) {
        this.sale = sale;
        this.lot = lot;
        this.quantity = quantity;
        this.id = new SaleDetailId(sale.getId(), lot.getId());
    }

    public SaleDetailId getId() {
        return id;
    }

    public void setId(SaleDetailId id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
