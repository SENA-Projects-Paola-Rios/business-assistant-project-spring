package com.sena.BusinessAssistantSpring.dto;

public class SaleDetailDTO {

    private Integer saleId;
    private Integer lotId;
    private Integer quantity;
    private String lotManufacturer;
    private String productName;

    public SaleDetailDTO() {}

    public SaleDetailDTO(Integer saleId, Integer lotId, Integer quantity, String lotManufacturer, String productName) {
        this.saleId = saleId;
        this.lotId = lotId;
        this.quantity = quantity;
        this.lotManufacturer = lotManufacturer;
        this.productName = productName;
    }

    // Getters y setters
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLotManufacturer() {
        return lotManufacturer;
    }

    public void setLotManufacturer(String lotManufacturer) {
        this.lotManufacturer = lotManufacturer;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
