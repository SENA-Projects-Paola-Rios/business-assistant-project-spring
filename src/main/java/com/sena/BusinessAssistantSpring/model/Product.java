package com.sena.BusinessAssistantSpring.model;

import com.sena.BusinessAssistantSpring.model.validation.Create;
import com.sena.BusinessAssistantSpring.model.validation.Update;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = Create.class, message = "ID must be null when creating a new product")
    @NotNull(groups = Update.class, message = "ID is required when updating")
    private Integer id;

    // El nombre del producto es obligatorio, no puede estar en blanco y debe tener entre 2 y 100 caracteres
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    // La descripción es opcional, pero se le establece un límite máximo por buenas prácticas
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    @Column(columnDefinition = "TEXT")
    private String description;

    // El precio es obligatorio y debe ser mayor a 0, con hasta 10 dígitos en total y 2 decimales
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Price must be a valid monetary amount with up to 8 digits and 2 decimals")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // Campo para manejar el borrado lógico: si tiene valor, el producto se considera eliminado
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Constructores
    public Product() {}

    public Product(Integer id, String name, String description, BigDecimal price, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.deletedAt = deletedAt;
    }

    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
