package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.dto.ProductCategoryDTO;
import com.sena.BusinessAssistantSpring.exception.ResourceNotFoundException;
import com.sena.BusinessAssistantSpring.model.ProductCategory;
import com.sena.BusinessAssistantSpring.model.ProductCategoryId;
import com.sena.BusinessAssistantSpring.service.ProductCategoryService;
import com.sena.BusinessAssistantSpring.service.ProductService;

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
    
    @Autowired
    private ProductService productService;

    /**
     * Obtener todas las asociaciones activas entre productos y categorías.
     */
    @GetMapping
    public ResponseEntity<List<ProductCategoryDTO>> getAll() {
        List<ProductCategoryDTO> dtoList = productCategoryService.findAll().stream()
            .map(pc -> new ProductCategoryDTO(
                pc.getProduct().getId(),
                pc.getProduct().getName(),
                pc.getCategory().getId(),
                pc.getCategory().getName()
            ))
            .toList();

        return ResponseEntity.ok(dtoList);
    }

    /**
     * Obtener una relación específica entre producto y categoría por IDs.
     */
    @GetMapping("/product/{productId}/category/{categoryId}")
    public ResponseEntity<ProductCategoryDTO> getById(@PathVariable int productId, @PathVariable int categoryId) {
        ProductCategoryId id = new ProductCategoryId(productId, categoryId);
        Optional<ProductCategory> optionalPc = productCategoryService.findById(id);

        if (optionalPc.isEmpty()) {
            throw new ResourceNotFoundException("ProductCategory not found");
        }

        ProductCategory pc = optionalPc.get();

        ProductCategoryDTO dto = new ProductCategoryDTO(
            pc.getProduct().getId(),
            pc.getProduct().getName(),
            pc.getCategory().getId(),
            pc.getCategory().getName()
        );

        return ResponseEntity.ok(dto);
    }

    /**
     * Obtener todas las relaciones activas de una categoría por ID de producto.
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductCategoryDTO>> getByProduct(@PathVariable int productId) {
        List<ProductCategory> list = productCategoryService.findByProductId(productId);

        List<ProductCategoryDTO> dtos = list.stream()
            .map(pc -> new ProductCategoryDTO(
                pc.getProduct().getId(),
                pc.getProduct().getName(),
                pc.getCategory().getId(),
                pc.getCategory().getName()
            ))
            .toList();

        return ResponseEntity.ok(dtos);
    }


    /**
     * Obtener todas las relaciones activas de productos asociadas a una categoría específica.
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductCategoryDTO>> getByCategory(@PathVariable int categoryId) {
        List<ProductCategory> list = productCategoryService.findByCategoryId(categoryId);

        List<ProductCategoryDTO> dtos = list.stream()
            .map(pc -> new ProductCategoryDTO(
                pc.getProduct().getId(),
                pc.getProduct().getName(),
                pc.getCategory().getId(),
                pc.getCategory().getName()
            ))
            .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * Crear una nueva asociación producto-categoría.
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductCategory pc) {
    	//System.out.println("creando" + pc.getProduct().getName() + " :: " +  pc.getCategory().getName());
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
        productCategoryService.delete(id);
        return ResponseEntity.ok("{\"message\": \"ProductCategory deleted successfully\"}");
    }
    
    /**
     * Eliminar todas las relaciones y registros asociados a un producto por productId
     */
    @DeleteMapping("/product/{productId}/categories")
    public ResponseEntity<?> deleteProductAssociations(@PathVariable int productId) {
        // Verificar si el producto existe (opcional)
        if (productService.findById(productId).isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"Product not found\"}");
        }

        // Se eliminan las categories asociadas:
        productCategoryService.deleteByProductId(productId);

        return ResponseEntity.ok("{\"message\": \"All categories of the product deleted successfully\"}");
    }
}
