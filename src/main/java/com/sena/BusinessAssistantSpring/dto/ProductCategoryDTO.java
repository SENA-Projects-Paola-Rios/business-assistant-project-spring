package com.sena.BusinessAssistantSpring.dto;

public class ProductCategoryDTO {

    private int productId;
    private String productName;
    private int categoryId;
    private String categoryName;

    public ProductCategoryDTO() {}

    public ProductCategoryDTO(int productId, String productName, int categoryId, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters y Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
