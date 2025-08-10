package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.dto.SaleDetailDTO;
import com.sena.BusinessAssistantSpring.exception.ResourceNotFoundException;
import com.sena.BusinessAssistantSpring.model.SaleDetail;
import com.sena.BusinessAssistantSpring.model.SaleDetailId;
import com.sena.BusinessAssistantSpring.service.SaleDetailService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sale-details")
public class SaleDetailRestController {

    @Autowired
    private SaleDetailService saleDetailService;

    /**
     * Obtener todos los detalles de venta activos (no eliminados lógicamente).
     */
    @GetMapping
    public ResponseEntity<List<SaleDetailDTO>> getAllSaleDetails() {
        // Convertimos las entidades a DTOs para evitar errores de serialización con Hibernate
        List<SaleDetailDTO> dtos = saleDetailService.findAll().stream()
            .map(detail -> new SaleDetailDTO(
                detail.getSale().getId(),
                detail.getLot().getId(),
                detail.getQuantity(),
                detail.getLot().getManufacturerLot(),
                detail.getLot().getProduct().getName(),
                detail.getLot().getProduct().getPrice()
            ))
            .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener un detalle de venta específico por su ID compuesto (saleId + lotId).
     */
    @GetMapping("/sale/{saleId}/lot/{lotId}")
    public ResponseEntity<SaleDetailDTO> getSaleDetailById(@PathVariable int saleId, @PathVariable int lotId) {
        SaleDetailId id = new SaleDetailId(saleId, lotId);
        Optional<SaleDetail> optional = saleDetailService.findById(id);

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("SaleDetail with given keys not found");
        }

        SaleDetail detail = optional.get();

        SaleDetailDTO dto = new SaleDetailDTO(
            detail.getSale().getId(),
            detail.getLot().getId(),
            detail.getQuantity(),
            detail.getLot().getManufacturerLot(),
            detail.getLot().getProduct().getName(),
            detail.getLot().getProduct().getPrice()
        );

        return ResponseEntity.ok(dto);
    }

    /**
     * Obtener todos los detalles de venta asociados a un ID de venta (saleId).
     */
    @GetMapping("/sale/{saleId}")
    public ResponseEntity<List<SaleDetailDTO>> getDetailsBySaleId(@PathVariable int saleId) {
        // Se filtran los detalles asociados a la venta y se mapean a DTOs
        List<SaleDetailDTO> dtos = saleDetailService.findBySaleId(saleId).stream()
            .map(detail -> new SaleDetailDTO(
                detail.getSale().getId(),
                detail.getLot().getId(),
                detail.getQuantity(),
                detail.getLot().getManufacturerLot(),
                detail.getLot().getProduct().getName(),
                detail.getLot().getProduct().getPrice()
            ))
            .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtener todos los detalles de venta asociados a un ID de lote (lotId).
     */
    @GetMapping("/lot/{lotId}")
    public ResponseEntity<List<SaleDetailDTO>> getDetailsByLotId(@PathVariable int lotId) {
        // Se filtran los detalles por lote y se transforman a DTOs
        List<SaleDetailDTO> dtos = saleDetailService.findByLotId(lotId).stream()
            .map(detail -> new SaleDetailDTO(
                detail.getSale().getId(),
                detail.getLot().getId(),
                detail.getQuantity(),
                detail.getLot().getManufacturerLot(),
                detail.getLot().getProduct().getName(),
                detail.getLot().getProduct().getPrice()
            ))
            .toList();

        return ResponseEntity.ok(dtos);
    }


    /**
     * Crear nuevo detalle de venta.
     */
    @PostMapping
    public ResponseEntity<?> createSaleDetail(@Valid @RequestBody SaleDetail saleDetail) {
        if (saleDetail.getId() == null) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"SaleDetail ID must not be null\"}");
        }
        saleDetailService.save(saleDetail);
        return ResponseEntity.ok("{\"message\": \"SaleDetail created successfully\"}");
    }

    /**
     * Eliminar detalle de venta (borrado lógico).
     */
    @DeleteMapping("/sale/{saleId}/lot/{lotId}")
    public ResponseEntity<?> deleteSaleDetail(@PathVariable int saleId, @PathVariable int lotId) {
        SaleDetailId id = new SaleDetailId(saleId, lotId);
        if (saleDetailService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"SaleDetail not found\"}");
        }
        saleDetailService.delete(id);
        return ResponseEntity.ok("{\"message\": \"SaleDetail deleted successfully\"}");
    }
}
