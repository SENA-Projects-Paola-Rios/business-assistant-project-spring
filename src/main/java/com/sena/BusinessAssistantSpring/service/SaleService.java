package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.model.Sale;
import com.sena.BusinessAssistantSpring.repository.SaleRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    // Listado de ventas activas
    public List<Sale> findAll() {
        return saleRepository.findByDeletedAtIsNull();
    }

    // Buscar una venta por ID, solo si está activa
    public Optional<Sale> findById(int id) {
        return saleRepository.findById(id)
                .filter(sale -> sale.getDeletedAt() == null);
    }

    // Guardar o actualizar venta
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    // Borrado lógico de venta
    @Transactional
    public boolean softDelete(int id) {
        return saleRepository.findById(id).map(sale -> {
            sale.setDeletedAt(LocalDateTime.now());
            saleRepository.save(sale);
            return true;
        }).orElse(false);
    }

    // Obtener ventas por usuario
    public List<Sale> findByUserId(int userId) {
        return saleRepository.findByUserIdAndDeletedAtIsNull(userId);
    }
}
