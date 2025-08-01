package com.sena.BusinessAssistantSpring.repository;

import com.sena.BusinessAssistantSpring.model.ProductCategory;
import com.sena.BusinessAssistantSpring.model.ProductCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryId> {

    // Obtener todas las relaciones de categoría-producto no eliminadas
    List<ProductCategory> findByDeletedAtIsNull();

    // Obtener todas las categorías asociadas a un producto específico
    List<ProductCategory> findByProductIdAndDeletedAtIsNull(Integer productId);

    // Obtener todos los productos asociados a una categoría específica
    List<ProductCategory> findByCategoryIdAndDeletedAtIsNull(Integer categoryId);
}
