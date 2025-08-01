package com.sena.BusinessAssistantSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_category")
public class ProductCategory {

    @EmbeddedId
    private ProductCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public ProductCategory() {}

    public ProductCategory(Product product, Category category) {
        this.product = product;
        this.category = category;
        this.id = new ProductCategoryId(product.getId(), category.getId());
    }

    public ProductCategoryId getId() {
        return id;
    }

    public void setId(ProductCategoryId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
