package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.exception.ResourceNotFoundException;
import com.sena.BusinessAssistantSpring.model.User;
import com.sena.BusinessAssistantSpring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Obtener todos los usuarios activos.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllActiveUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Obtener un usuario por su ID.
     * Si no se encuentra, retorna 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getById(id);
        return user.map(ResponseEntity::ok)
        			.orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    /**
     * Crear un nuevo usuario.
     * Se valida que no venga con ID, ya que se genera automáticamente.
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        if (user.getId() != null) {
            return ResponseEntity.badRequest()
                                 .body("{\"message\": \"User ID must be null when creating a new user\"}");
        }
        
        // Validación: la contraseña no puede estar vacía
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "Password must not be empty"));
        }

        // Encriptar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        userService.save(user);
        return ResponseEntity.ok("{\"message\": \"User created successfully\"}");
    }

    /**
     * Actualizar un usuario existente.
     * Se verifica primero que el usuario exista.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @Valid @RequestBody User user) {
        Optional<User> existingUser = userService.getById(id);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"User not found\"}");
        }
        user.setId(id);
     // Encriptar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
        return ResponseEntity.ok("{\"message\": \"User updated successfully\"}");
    }

    /**
     * Eliminar un usuario de forma lógica (soft delete).
     * Marca el estado como inactivo en lugar de borrarlo de la base de datos.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        Optional<User> user = userService.getById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"User not found\"}");
        }
        userService.softDelete(id);
        return ResponseEntity.ok("{\"message\": \"User deleted successfully\"}");
    }
}
