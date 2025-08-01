package com.sena.BusinessAssistantSpring.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductCategoryId implements Serializable {

    private Integer productId;
    private Integer categoryId;

    public ProductCategoryId() {}

    public ProductCategoryId(Integer productId, Integer categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCategoryId)) return false;
        ProductCategoryId that = (ProductCategoryId) o;
        return Objects.equals(productId, that.productId) &&
               Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, categoryId);
    }
}
