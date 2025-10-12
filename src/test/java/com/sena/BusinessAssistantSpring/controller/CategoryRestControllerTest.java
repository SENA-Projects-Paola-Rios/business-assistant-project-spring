package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.model.Category;
import com.sena.BusinessAssistantSpring.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryRestControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryRestController categoryRestController;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setId(1);
        category.setName("Bebidas");
        category.setDescription("Categoría para bebidas frías y calientes");
        category.setDeletedAt(null);
    }

    @Test
    void testGetAllCategories() {
        when(categoryService.findAll()).thenReturn(List.of(category));

        ResponseEntity<List<Category>> response = categoryRestController.getAllCategories();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetCategoryById_Found() {
        when(categoryService.findById(1)).thenReturn(Optional.of(category));

        ResponseEntity<?> response = categoryRestController.getCategoryById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Category);
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryService.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryRestController.getCategoryById(1);
        });

        assertTrue(exception.getMessage().contains("Category with ID 1 not found"));
    }

    @Test
    void testCreateCategory_Success() {
        Category newCategory = new Category(null, "Snacks", "Categoría de snacks", null);

        ResponseEntity<?> response = categoryRestController.createCategory(newCategory);

        assertEquals(200, response.getStatusCodeValue());
        verify(categoryService, times(1)).save(any(Category.class));
    }

    @Test
    void testCreateCategory_WithId_ShouldFail() {
        Category newCategory = new Category(10, "Snacks", "Categoría de snacks", null);

        ResponseEntity<?> response = categoryRestController.createCategory(newCategory);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Category ID must be null"));
    }

    @Test
    void testUpdateCategory_Success() {
        when(categoryService.findById(1)).thenReturn(Optional.of(category));

        category.setName("Bebidas Actualizadas");
        ResponseEntity<?> response = categoryRestController.updateCategory(1, category);

        assertEquals(200, response.getStatusCodeValue());
        verify(categoryService, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdateCategory_NotFound() {
        when(categoryService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = categoryRestController.updateCategory(1, category);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Category not found"));
    }

    @Test
    void testDeleteCategory_Success() {
        when(categoryService.findById(1)).thenReturn(Optional.of(category));

        ResponseEntity<?> response = categoryRestController.deleteCategory(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(categoryService, times(1)).softDelete(1);
    }

    @Test
    void testDeleteCategory_NotFound() {
        when(categoryService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = categoryRestController.deleteCategory(1);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Category not found"));
    }
}
