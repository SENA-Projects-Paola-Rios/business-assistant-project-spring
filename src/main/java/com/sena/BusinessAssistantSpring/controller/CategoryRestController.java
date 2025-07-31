package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.exception.ResourceNotFoundException;
import com.sena.BusinessAssistantSpring.model.Category;
import com.sena.BusinessAssistantSpring.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Obtener todas las categorías activas.
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    /**
     * Obtener una categoría por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id) {
        Optional<Category> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + id + " not found"));
    }

    /**
     * Crear una nueva categoría.
     */
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
        if (category.getId() != null) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Category ID must be null when creating a new category\"}");
        }
        categoryService.save(category);
        return ResponseEntity.ok("{\"message\": \"Category created successfully\"}");
    }

    /**
     * Actualizar una categoría existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @Valid @RequestBody Category category) {
        Optional<Category> existingCategory = categoryService.findById(id);
        if (existingCategory.isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"Category not found\"}");
        }
        category.setId(id);
        categoryService.save(category);
        return ResponseEntity.ok("{\"message\": \"Category updated successfully\"}");
    }

    /**
     * Eliminar una categoría (soft delete).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"Category not found\"}");
        }
        categoryService.softDelete(id);
        return ResponseEntity.ok("{\"message\": \"Category deleted successfully\"}");
    }
}
