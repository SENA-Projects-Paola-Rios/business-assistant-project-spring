package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.exception.BusinessValidationException;
import com.sena.BusinessAssistantSpring.model.Lot;
import com.sena.BusinessAssistantSpring.repository.LotRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LotService {

    @Autowired
    private LotRepository lotRepository;

    // Devuelve todos los lotes que no han sido eliminados
    public List<Lot> findAll() {
        return lotRepository.findByDeletedAtIsNull();
    }

    // Busca un lote por ID, solo si no está eliminado
    public Optional<Lot> findById(int id) {
        return lotRepository.findById(id)
                .filter(lot -> lot.getDeletedAt() == null);
    }

    // Guarda o actualiza un lote, validando unicidad por manufacturerLot
    public Lot save(Lot lot) {
        List<ObjectError> businessErrors = new ArrayList<>();

        if (lotRepository.existsByManufacturerLotAndIdNot(
                lot.getManufacturerLot(), lot.getId() == null ? -1 : lot.getId())) {
            businessErrors.add(new ObjectError("manufacturerLot", "The manufacturer lot code is already in use"));
        }

        if (!businessErrors.isEmpty()) {
            throw new BusinessValidationException(businessErrors);
        }

        return lotRepository.save(lot);
    }

    // Borrado lógico del lote
    @Transactional
    public boolean softDelete(int id) {
        return lotRepository.findById(id).map(lot -> {
            lot.setDeletedAt(LocalDateTime.now());
            lotRepository.save(lot);
            return true;
        }).orElse(false);
    }
}
