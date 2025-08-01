package com.sena.BusinessAssistantSpring.model;

import com.sena.BusinessAssistantSpring.model.validation.Create;
import com.sena.BusinessAssistantSpring.model.validation.Update;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = Create.class, message = "ID must be null when creating a new sale")
    @NotNull(groups = Update.class, message = "ID is required when updating")
    private Integer id;

    @NotNull(message = "Sale date is required")
    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @DecimalMin(value = "0.00", inclusive = true, message = "Total must be non-negative")
    @Digits(integer = 10, fraction = 2, message = "Total must be a valid monetary amount")
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Sale() {}

    public Sale(LocalDate saleDate, BigDecimal total, User user) {
        this.saleDate = saleDate;
        this.total = total;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
