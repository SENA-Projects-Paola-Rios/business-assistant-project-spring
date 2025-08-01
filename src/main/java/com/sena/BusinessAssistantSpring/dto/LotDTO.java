package com.sena.BusinessAssistantSpring.dto;

import java.time.LocalDate;

public class LotDTO {

    private Integer id;
    private String manufacturerLot;
    private LocalDate expirationDate;
    private int stock;
    private Integer productId;
    private String productName;

    public LotDTO() {}

    public LotDTO(Integer id, String manufacturerLot, LocalDate expirationDate, int stock, Integer productId, String productName) {
        this.id = id;
        this.manufacturerLot = manufacturerLot;
        this.expirationDate = expirationDate;
        this.stock = stock;
        this.productId = productId;
        this.productName = productName;
    }

    // Getters y setters
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
