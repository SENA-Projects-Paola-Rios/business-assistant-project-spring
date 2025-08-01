package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.exception.ResourceNotFoundException;
import com.sena.BusinessAssistantSpring.model.ProductCategory;
import com.sena.BusinessAssistantSpring.model.ProductCategoryId;
import com.sena.BusinessAssistantSpring.service.ProductCategoryService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryRestController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * Obtener todas las asociaciones activas entre productos y categorías.
     */
    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAll() {
        return ResponseEntity.ok(productCategoryService.findAll());
    }

    /**
     * Obtener relación producto-categoría por ID compuesto.
     */
    @GetMapping("/product/{productId}/category/{categoryId}")
    public ResponseEntity<?> getById(@PathVariable int productId, @PathVariable int categoryId) {
        ProductCategoryId id = new ProductCategoryId(productId, categoryId);
        Optional<ProductCategory> pc = productCategoryService.findById(id);
        return pc.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("ProductCategory not found"));
    }

    /**
     * Obtener relaciones por producto.
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductCategory>> getByProduct(@PathVariable int productId) {
        return ResponseEntity.ok(productCategoryService.findByProductId(productId));
    }

    /**
     * Obtener relaciones por categoría.
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductCategory>> getByCategory(@PathVariable int categoryId) {
        return ResponseEntity.ok(productCategoryService.findByCategoryId(categoryId));
    }

    /**
     * Crear una nueva asociación producto-categoría.
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductCategory pc) {
        if (pc.getId() == null) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"ProductCategory ID must not be null\"}");
        }
        productCategoryService.save(pc);
        return ResponseEntity.ok("{\"message\": \"ProductCategory created successfully\"}");
    }

    /**
     * Eliminar relación producto-categoría (soft delete).
     */
    @DeleteMapping("/product/{productId}/category/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable int productId, @PathVariable int categoryId) {
        ProductCategoryId id = new ProductCategoryId(productId, categoryId);
        if (productCategoryService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"ProductCategory not found\"}");
        }
        productCategoryService.softDelete(id);
        return ResponseEntity.ok("{\"message\": \"ProductCategory deleted successfully\"}");
    }
}
