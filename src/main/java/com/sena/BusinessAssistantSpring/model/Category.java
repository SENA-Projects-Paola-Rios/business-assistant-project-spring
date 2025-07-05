package com.sena.BusinessAssistantSpring.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Cambiado de int a Integer

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Category() {
    }

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
