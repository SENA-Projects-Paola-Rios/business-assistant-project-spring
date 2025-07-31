package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.exception.BusinessValidationException;
import com.sena.BusinessAssistantSpring.model.User;
import com.sena.BusinessAssistantSpring.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    
    //guarda una usuario
    public User save(User user) {
    	
    	List<ObjectError> businessErrors = new ArrayList<>();
    	
    	if (userRepository.existsByEmail(user.getEmail())) {
            businessErrors.add(new ObjectError("email", "The email address is already in use"));
        }
    	
    	validatePasswordStrength(user.getPassword(), businessErrors);
    	
        if (!businessErrors.isEmpty()) {
            throw new BusinessValidationException(businessErrors);
        }
        
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
    	Optional<User> existing = userRepository.findById(user.getId());
        if (existing.isPresent()) {
            User userToUpdate = existing.get();
            
            List<ObjectError> businessErrors = new ArrayList<>();
            
            User existingByEmail = null;
            
            Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

            if (optionalUser.isPresent()) {
                existingByEmail = optionalUser.get();
                // Ya existe un usuario con ese correo
            }
            
            if (existingByEmail != null && existingByEmail.getId() != userToUpdate.getId()) {
                businessErrors.add(new ObjectError("email", "The email address is already in use"));
            }

            if (!businessErrors.isEmpty()) {
                throw new BusinessValidationException(businessErrors);
            }
            
            userToUpdate.setName(user.getName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setRole(user.getRole());
            
            // Solo actualiza el password si viene uno nuevo
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
            	
            	validatePasswordStrength(user.getPassword(), businessErrors);
            	
            	if (!businessErrors.isEmpty()) {
                    throw new BusinessValidationException(businessErrors);
                }
            	
                userToUpdate.setPassword(user.getPassword());
            }

            return userRepository.save(userToUpdate);
        }
    	return null;
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
    	Optional<User> optionalUser = userRepository.findByEmail(email);

    	if (optionalUser.isPresent()) {
    	    return optionalUser.get();
    	}
        return null;
    }
    
    //validacion para verificar el password como regla de negocio
    private void validatePasswordStrength(String password, List<ObjectError> businessErrors) {
        if (password == null || password.isBlank()) {
            return; // No validar si está vacía (esto se maneja solo en creación)
        }

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

        if (!password.matches(regex)) {
            businessErrors.add(new ObjectError("password", "Password must be at least 8 characters long and include one uppercase letter, one lowercase letter, and one number"));
        }
    }
}
