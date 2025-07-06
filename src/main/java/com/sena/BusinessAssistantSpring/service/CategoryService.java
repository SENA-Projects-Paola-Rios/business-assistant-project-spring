package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.exception.BusinessValidationException;
import com.sena.BusinessAssistantSpring.model.Category;
import com.sena.BusinessAssistantSpring.repository.CategoryRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    //devuelve todo el listado de categorias
    public List<Category> findAll() {
        return categoryRepository.findByDeletedAtIsNull();
    }

    //busca un categoria por id
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id)
                .filter(c -> c.getDeletedAt() == null);
    }

    //guarda una categoria
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

    //nos permite borrar un usuario de manera logica colocando una fecha de borrado en la columna deleted at
    @Transactional
    public void softDelete(int id) {
        categoryRepository.deleteById(id);
    }
}
