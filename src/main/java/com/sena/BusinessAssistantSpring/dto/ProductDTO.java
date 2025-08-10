package com.sena.BusinessAssistantSpring.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<CategoryDTO> categories;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}

    
}