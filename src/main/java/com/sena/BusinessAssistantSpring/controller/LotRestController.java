package com.sena.BusinessAssistantSpring.controller;

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
     * Obtener todos los lotes activos.
     */
    @GetMapping
    public ResponseEntity<List<Lot>> getAllLots() {
        return ResponseEntity.ok(lotService.findAll());
    }

    /**
     * Obtener un lote por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getLotById(@PathVariable int id) {
        Optional<Lot> lot = lotService.findById(id);
        return lot.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Lot with ID " + id + " not found"));
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
