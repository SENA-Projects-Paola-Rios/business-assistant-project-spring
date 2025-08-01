package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.dto.SaleDTO;
import com.sena.BusinessAssistantSpring.exception.ResourceNotFoundException;
import com.sena.BusinessAssistantSpring.model.Sale;
import com.sena.BusinessAssistantSpring.service.SaleService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
public class SaleRestController {

    @Autowired
    private SaleService saleService;

    /**
     * Obtener todas las ventas activas (no eliminadas).
     */
    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        List<SaleDTO> dtos = saleService.findAll().stream()
            .map(sale -> new SaleDTO(
                sale.getId(),
                sale.getSaleDate(),
                sale.getTotal(),
                sale.getUser().getId(),
                sale.getUser().getName()
            ))
            .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener una venta específica por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSaleById(@PathVariable int id) {
        Optional<Sale> optionalSale = saleService.findById(id);

        if (optionalSale.isEmpty()) {
            throw new ResourceNotFoundException("Sale with ID " + id + " not found");
        }

        Sale sale = optionalSale.get();

        SaleDTO dto = new SaleDTO(
            sale.getId(),
            sale.getSaleDate(),
            sale.getTotal(),
            sale.getUser().getId(),
            sale.getUser().getName()
        );

        return ResponseEntity.ok(dto);
    }

    /**
     * Obtener todas las ventas realizadas por un usuario específico.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SaleDTO>> getSalesByUserId(@PathVariable int userId) {
        List<SaleDTO> dtos = saleService.findByUserId(userId).stream()
            .map(sale -> new SaleDTO(
                sale.getId(),
                sale.getSaleDate(),
                sale.getTotal(),
                sale.getUser().getId(),
                sale.getUser().getName()
            ))
            .toList();

        return ResponseEntity.ok(dtos);
    }


    /**
     * Crear una nueva venta.
     */
    @PostMapping
    public ResponseEntity<?> createSale(@Valid @RequestBody Sale sale) {
        if (sale.getId() != null) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Sale ID must be null when creating a new sale\"}");
        }
        saleService.save(sale);
        return ResponseEntity.ok("{\"message\": \"Sale created successfully\"}");
    }

    /**
     * Actualizar una venta existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSale(@PathVariable int id, @Valid @RequestBody Sale sale) {
        if (saleService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"Sale not found\"}");
        }
        sale.setId(id);
        saleService.save(sale);
        return ResponseEntity.ok("{\"message\": \"Sale updated successfully\"}");
    }

    /**
     * Eliminar una venta (soft delete).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable int id) {
        if (saleService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"Sale not found\"}");
        }
        saleService.softDelete(id);
        return ResponseEntity.ok("{\"message\": \"Sale deleted successfully\"}");
    }
}
