package com.sena.BusinessAssistantSpring.repository;

import com.sena.BusinessAssistantSpring.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Integer> {

    // Obtener todos los lotes que no han sido eliminados
    List<Lot> findByDeletedAtIsNull();

    // Verifica si existe un lote con el mismo código de fabricante (evita duplicados al crear)
    boolean existsByManufacturerLot(String manufacturerLot);

    // Verifica si existe un lote con el mismo código pero diferente ID (evita duplicados al actualizar)
    boolean existsByManufacturerLotAndIdNot(String manufacturerLot, Integer id);

    // Obtener todos los lotes de un producto específico que no han sido eliminados
    List<Lot> findByProductIdAndDeletedAtIsNull(Integer productId);
}
