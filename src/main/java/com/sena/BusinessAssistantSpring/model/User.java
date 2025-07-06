package com.sena.BusinessAssistantSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "The name is required")
    @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "The email is required")
    @Email(message = "The email must be valid")
    @Size(max = 100, message = "The email must be less than 100 characters")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "The password is required")
    @Size(min = 6, max = 100, message = "The password must be between 6 and 100 characters")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "The role is required")
    @Pattern(regexp = "^(ADMIN|USER|MANAGER)$", message = "The role must be ADMIN, USER, or MANAGER")
    @Column(nullable = false)
    private String role;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Constructor vacío requerido por JPA
    public User() {}

    // Constructor completo
    public User(int id, String name, String email, String password, String role, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.deletedAt = deletedAt;
    }

    // Constructor para nuevo usuario
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
