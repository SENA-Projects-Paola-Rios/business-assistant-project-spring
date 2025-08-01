package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.model.SaleDetail;
import com.sena.BusinessAssistantSpring.model.SaleDetailId;
import com.sena.BusinessAssistantSpring.repository.SaleDetailRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SaleDetailService {

    @Autowired
    private SaleDetailRepository saleDetailRepository;

    // Listado completo de detalles (sin eliminados)
    public List<SaleDetail> findAll() {
        return saleDetailRepository.findAll().stream()
                .filter(detail -> detail.getDeletedAt() == null)
                .toList();
    }

    // Buscar detalle por ID compuesto
    public Optional<SaleDetail> findById(SaleDetailId id) {
        return saleDetailRepository.findById(id)
                .filter(detail -> detail.getDeletedAt() == null);
    }

    // Guardar nuevo detalle
    public SaleDetail save(SaleDetail detail) {
        return saleDetailRepository.save(detail);
    }

    // Borrado lógico
    @Transactional
    public boolean softDelete(SaleDetailId id) {
        return saleDetailRepository.findById(id).map(detail -> {
            detail.setDeletedAt(LocalDateTime.now());
            saleDetailRepository.save(detail);
            return true;
        }).orElse(false);
    }

    // Borrado fisico
    @Transactional
    public boolean delete(SaleDetailId id) {
        return saleDetailRepository.findById(id).map(detail -> {
            saleDetailRepository.delete(detail);  // Borrado físico del registro
            return true;
        }).orElse(false);
    }


    // Obtener detalles por venta
    public List<SaleDetail> findBySaleId(int saleId) {
        return saleDetailRepository.findBySaleIdAndDeletedAtIsNull(saleId);
    }

    // Obtener detalles por lote
    public List<SaleDetail> findByLotId(int lotId) {
        return saleDetailRepository.findByLotIdAndDeletedAtIsNull(lotId);
    }
}
