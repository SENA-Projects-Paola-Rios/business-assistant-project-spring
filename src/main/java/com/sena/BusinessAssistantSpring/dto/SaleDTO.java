package com.sena.BusinessAssistantSpring.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SaleDTO {

    private Integer id;
    private LocalDate saleDate;
    private BigDecimal total;
    private Integer userId;
    private String userName;

    public SaleDTO() {}

    public SaleDTO(Integer id, LocalDate saleDate, BigDecimal total, Integer userId, String userName) {
        this.id = id;
        this.saleDate = saleDate;
        this.total = total;
        this.userId = userId;
        this.userName = userName;
    }

    // Getters y setters
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
