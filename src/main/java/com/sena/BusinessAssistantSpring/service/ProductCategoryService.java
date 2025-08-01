package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.model.ProductCategory;
import com.sena.BusinessAssistantSpring.model.ProductCategoryId;
import com.sena.BusinessAssistantSpring.repository.ProductCategoryRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    // Listar asociaciones activas
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findByDeletedAtIsNull();
    }

    // Buscar relación específica
    public Optional<ProductCategory> findById(ProductCategoryId id) {
        return productCategoryRepository.findById(id)
                .filter(pc -> pc.getDeletedAt() == null);
    }

    // Guardar nueva relación producto-categoría
    public ProductCategory save(ProductCategory pc) {
        return productCategoryRepository.save(pc);
    }

    // Borrado lógico
    @Transactional
    public boolean softDelete(ProductCategoryId id) {
        return productCategoryRepository.findById(id).map(pc -> {
            pc.setDeletedAt(LocalDateTime.now());
            productCategoryRepository.save(pc);
            return true;
        }).orElse(false);
    }

    // Buscar relaciones por producto
    public List<ProductCategory> findByProductId(int productId) {
        return productCategoryRepository.findByProductIdAndDeletedAtIsNull(productId);
    }

    // Buscar relaciones por categoría
    public List<ProductCategory> findByCategoryId(int categoryId) {
        return productCategoryRepository.findByCategoryIdAndDeletedAtIsNull(categoryId);
    }
}
