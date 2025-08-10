package com.sena.BusinessAssistantSpring.repository;

import com.sena.BusinessAssistantSpring.model.Product;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Personalizado: obtener solo productos que no han sido eliminados (borrado lógico)
    List<Product> findByDeletedAtIsNull();

    // Verifica si existe un producto con el mismo nombre
    boolean existsByName(String name);

    // Verifica si existe un producto con el mismo nombre pero distinto ID (para evitar duplicados al actualizar)
    boolean existsByNameAndIdNot(String name, Integer id);
    
    
}
