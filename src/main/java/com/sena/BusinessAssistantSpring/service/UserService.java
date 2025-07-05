package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.model.User;
import com.sena.BusinessAssistantSpring.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // ✅ Constructor explícito para evitar errores en entornos avanzados
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Crear usuario
    public User create(User user) {
        return userRepository.save(user);
    }

    // Listar usuarios activos (no eliminados lógicamente)
    public List<User> getAllActiveUsers() {
        return userRepository.findAllByDeletedAtIsNull();
    }

    // Buscar usuario por ID
    public Optional<User> getById(int id) {
        return userRepository.findByIdAndDeletedAtIsNull(id);
    }

    // Actualizar usuario
    public User update(User user) {
        return userRepository.save(user);
    }

    // Eliminación lógica (soft delete)
    public boolean softDelete(int id) {
        return userRepository.findById(id).map(user -> {
            user.setDeletedAt(LocalDateTime.now());
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

    // Autenticación de usuario
    public User login(String email, String password) {
        return userRepository.findByEmailAndPasswordAndDeletedAtIsNull(email, password);
    }
    
    //Busca un usuario por email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
