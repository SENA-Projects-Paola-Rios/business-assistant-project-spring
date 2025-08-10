package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.dto.LotDTO;
import com.sena.BusinessAssistantSpring.exception.ResourceNotFoundException;
import com.sena.BusinessAssistantSpring.model.Lot;
import com.sena.BusinessAssistantSpring.service.LotService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lots")
public class LotRestController {

    @Autowired
    private LotService lotService;

    /**
     * Obtener todos los lotes activos (que no han sido eliminados lógicamente).
     */
    @GetMapping
    public ResponseEntity<List<LotDTO>> getAllLots() {
        List<LotDTO> dtos = lotService.findAll().stream()
            .map(lot -> new LotDTO(
                lot.getId(),
                lot.getManufacturerLot(),
                lot.getExpirationDate(),
                lot.getStock(),
                lot.getProduct().getId(),
                lot.getProduct().getName(),
                lot.getProduct().getPrice()
            ))
            .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener un lote específico por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LotDTO> getLotById(@PathVariable int id) {
        Optional<Lot> lot = lotService.findById(id);

        if (lot.isEmpty()) {
            throw new ResourceNotFoundException("Lot with ID " + id + " not found");
        }

        Lot entity = lot.get();

        LotDTO dto = new LotDTO(
            entity.getId(),
            entity.getManufacturerLot(),
            entity.getExpirationDate(),
            entity.getStock(),
            entity.getProduct().getId(),
            entity.getProduct().getName(),
            entity.getProduct().getPrice()
        );

        return ResponseEntity.ok(dto);
    }

    /**
     * Crear un nuevo lote.
     */
    @PostMapping
    public ResponseEntity<?> createLot(@Valid @RequestBody Lot lot) {
        if (lot.getId() != null) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Lot ID must be null when creating a new lot\"}");
        }
        lotService.save(lot);
        return ResponseEntity.ok("{\"message\": \"Lot created successfully\"}");
    }

    /**
     * Actualizar un lote existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLot(@PathVariable int id, @Valid @RequestBody Lot lot) {
        if (lotService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"Lot not found\"}");
        }
        lot.setId(id);
        lotService.save(lot);
        return ResponseEntity.ok("{\"message\": \"Lot updated successfully\"}");
    }

    /**
     * Eliminar un lote (borrado lógico).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLot(@PathVariable int id) {
        if (lotService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"Lot not found\"}");
        }
        lotService.softDelete(id);
        return ResponseEntity.ok("{\"message\": \"Lot deleted successfully\"}");
    }
}
