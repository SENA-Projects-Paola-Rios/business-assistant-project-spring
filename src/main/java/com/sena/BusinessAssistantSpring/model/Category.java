package com.sena.BusinessAssistantSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

import com.sena.BusinessAssistantSpring.model.validation.Create;
import com.sena.BusinessAssistantSpring.model.validation.Update;

@Entity
@Table(name = "category")
public class Category {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = Create.class, message = "ID must be null when creating a new category")
    @NotNull(groups = Update.class, message = "ID is required when updating")
    private Integer id;

	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
	@Pattern(regexp = "^[A-Za-zÀ-ÿ0-9\\s-]+$", message = "Name can only contain letters, numbers, spaces and hyphens")
	private String name;
	
	@NotBlank(message = "Description is required")
	@Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
	@Pattern(regexp = "^[A-Za-zÀ-ÿ0-9\\s.,;:()'\"¡!¿?/-]*$", message = "Description contains invalid characters")
	private String description;
    
    

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Category() {}

    public Category(Integer id, String name, String description, Timestamp deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deletedAt = deletedAt;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters y Setters

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
        if (!name.equals("Hygiene") && !name.equals("Medicine") &&
            !name.equals("Food") && !name.equals("Beverages")) {
            throw new IllegalArgumentException("Invalid category name: " + name);
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}
