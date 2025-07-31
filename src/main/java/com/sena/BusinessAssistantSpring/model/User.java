package com.sena.BusinessAssistantSpring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sena.BusinessAssistantSpring.model.validation.Create;
import com.sena.BusinessAssistantSpring.model.validation.Update;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = Create.class, message = "ID must be null when creating a new user")
    @NotNull(groups = Update.class, message = "ID is required when updating")
    private Integer id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s'-]+$", message = "Name can only contain letters, spaces, apostrophes and hyphens")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    @Column(nullable = false, unique = true)
    private String email;

    
    @NotBlank(groups = Create.class, message = "Password is required")
    @Size(groups = Create.class, min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(ADMIN|USER|MANAGER)$", message = "Role must be ADMIN, USER, or MANAGER")
    @Column(nullable = false)
    private String role;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Constructors

    public User() {}

    public User(Integer id, String name, String email, String password, String role, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.deletedAt = deletedAt;
    }

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and setters

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
