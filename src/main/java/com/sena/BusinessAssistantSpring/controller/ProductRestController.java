package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.exception.ResourceNotFoundException;
import com.sena.BusinessAssistantSpring.model.Product;
import com.sena.BusinessAssistantSpring.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    /**
     * Obtener todos los productos activos (no eliminados).
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    /**
     * Obtener un producto por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));
    }

    /**
     * Crear un nuevo producto.
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
        if (product.getId() != null) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Product ID must be null when creating a new product\"}");
        }
        productService.save(product);
        return ResponseEntity.ok("{\"message\": \"Product created successfully\"}");
    }

    /**
     * Actualizar un producto existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @Valid @RequestBody Product product) {
        Optional<Product> existingProduct = productService.findById(id);
        if (existingProduct.isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"Product not found\"}");
        }
        product.setId(id);
        productService.save(product);
        return ResponseEntity.ok("{\"message\": \"Product updated successfully\"}");
    }

    /**
     * Eliminar un producto (soft delete).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        Optional<Product> product = productService.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"Product not found\"}");
        }
        productService.softDelete(id);
        return ResponseEntity.ok("{\"message\": \"Product deleted successfully\"}");
    }
}
