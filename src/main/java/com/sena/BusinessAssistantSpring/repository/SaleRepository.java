package com.sena.BusinessAssistantSpring.repository;

import com.sena.BusinessAssistantSpring.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    // Obtener todas las ventas que no han sido eliminadas
    List<Sale> findByDeletedAtIsNull();

    // Obtener ventas por usuario (no eliminadas)
    List<Sale> findByUserIdAndDeletedAtIsNull(Integer userId);
}
