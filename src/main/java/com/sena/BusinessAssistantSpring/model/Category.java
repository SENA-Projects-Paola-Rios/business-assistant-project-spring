package com.sena.BusinessAssistantSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.sql.Timestamp;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The name is required")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters")
    @Pattern(regexp = "^(Hygiene|Medicine|Food|Beverages)$", message = "Category name must be one of: Hygiene, Medicine, Food, Beverages")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "The description is required")
    @Size(min = 5, max = 200, message = "The description must be between 5 and 200 characters")
    @Column(nullable = false)
    private String description;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Category() {}

    // Constructor completo
    public Category(Integer id, String name, String description, Timestamp deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deletedAt = deletedAt;
    }

    // Constructor para nuevos registros
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
