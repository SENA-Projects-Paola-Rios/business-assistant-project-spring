package com.sena.BusinessAssistantSpring.repository;

import com.sena.BusinessAssistantSpring.model.SaleDetail;
import com.sena.BusinessAssistantSpring.model.SaleDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, SaleDetailId> {

    // Obtener todos los detalles de una venta específica
    List<SaleDetail> findBySaleIdAndDeletedAtIsNull(Integer saleId);

    // Obtener todos los detalles asociados a un lote específico
    List<SaleDetail> findByLotIdAndDeletedAtIsNull(Integer lotId);
}
