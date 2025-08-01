package com.sena.BusinessAssistantSpring.model;

import com.sena.BusinessAssistantSpring.model.validation.Create;
import com.sena.BusinessAssistantSpring.model.validation.Update;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lot")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = Create.class, message = "ID must be null when creating a new lot")
    @NotNull(groups = Update.class, message = "ID is required when updating")
    private Integer id;

    @NotBlank(message = "Manufacturer lot is required")
    @Size(max = 12, message = "Manufacturer lot must be 12 characters or fewer")
    @Column(name = "manufacturer_lot", nullable = false, unique = true, length = 12)
    private String manufacturerLot;

    @NotNull(message = "Expiration date is required")
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Min(value = 0, message = "Stock cannot be negative")
    @Column(nullable = false)
    private Integer stock = 0;

    @NotNull(message = "Product is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Lot() {}

    public Lot(String manufacturerLot, LocalDate expirationDate, Integer stock, Product product) {
        this.manufacturerLot = manufacturerLot;
        this.expirationDate = expirationDate;
        this.stock = stock;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManufacturerLot() {
        return manufacturerLot;
    }

    public void setManufacturerLot(String manufacturerLot) {
        this.manufacturerLot = manufacturerLot;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
