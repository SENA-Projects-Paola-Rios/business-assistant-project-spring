package com.sena.BusinessAssistantSpring.repository;

import com.sena.BusinessAssistantSpring.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Personalizado: obtener solo categorías no eliminadas
    List<Category> findByDeletedAtIsNull();
    
    boolean existsByName(String name);
}
