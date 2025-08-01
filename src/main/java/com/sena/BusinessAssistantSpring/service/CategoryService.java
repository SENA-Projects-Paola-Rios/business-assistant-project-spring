package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.exception.BusinessValidationException;
import com.sena.BusinessAssistantSpring.model.Category;
import com.sena.BusinessAssistantSpring.repository.CategoryRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Devuelve todo el listado de categorías no eliminadas
    public List<Category> findAll() {
        return categoryRepository.findByDeletedAtIsNull();
    }

    // Busca una categoría por ID (si no ha sido eliminada)
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id)
                .filter(c -> c.getDeletedAt() == null);
    }

    // Guarda una categoría (con validación de nombre único)
    public Category save(Category category) {
        List<ObjectError> businessErrors = new ArrayList<>();

        if (categoryRepository.existsByName(category.getName())) {
            businessErrors.add(new ObjectError("name", "The category name is already in use"));
        }

        if (!businessErrors.isEmpty()) {
            throw new BusinessValidationException(businessErrors);
        }

        return categoryRepository.save(category);
    }

    // Realiza un borrado lógico de la categoría (marca deletedAt con la fecha actual)
    @Transactional
    public boolean softDelete(int id) {
        return categoryRepository.findById(id).map(category -> {
        	category.setDeletedAt(java.sql.Timestamp.valueOf(LocalDateTime.now()));
            categoryRepository.save(category);
            return true;
        }).orElse(false);
    }
}
