package com.sena.BusinessAssistantSpring.controller;

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
     * Obtener todos los detalles de venta activos.
     */
    @GetMapping
    public ResponseEntity<List<SaleDetail>> getAllSaleDetails() {
        return ResponseEntity.ok(saleDetailService.findAll());
    }

    /**
     * Obtener detalles de venta por ID compuesto.
     */
    @GetMapping("/sale/{saleId}/lot/{lotId}")
    public ResponseEntity<?> getSaleDetailById(@PathVariable int saleId, @PathVariable int lotId) {
        SaleDetailId id = new SaleDetailId(saleId, lotId);
        Optional<SaleDetail> detail = saleDetailService.findById(id);
        return detail.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("SaleDetail with given keys not found"));
    }

    /**
     * Obtener detalles por ID de venta.
     */
    @GetMapping("/sale/{saleId}")
    public ResponseEntity<List<SaleDetail>> getDetailsBySaleId(@PathVariable int saleId) {
        return ResponseEntity.ok(saleDetailService.findBySaleId(saleId));
    }

    /**
     * Obtener detalles por ID de lote.
     */
    @GetMapping("/lot/{lotId}")
    public ResponseEntity<List<SaleDetail>> getDetailsByLotId(@PathVariable int lotId) {
        return ResponseEntity.ok(saleDetailService.findByLotId(lotId));
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
        saleDetailService.softDelete(id);
        return ResponseEntity.ok("{\"message\": \"SaleDetail deleted successfully\"}");
    }
}
