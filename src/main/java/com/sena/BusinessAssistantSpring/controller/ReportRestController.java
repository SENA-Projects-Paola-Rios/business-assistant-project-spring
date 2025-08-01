package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.exception.ResourceNotFoundException;
import com.sena.BusinessAssistantSpring.model.Report;
import com.sena.BusinessAssistantSpring.model.ReportType;
import com.sena.BusinessAssistantSpring.service.ReportService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    @Autowired
    private ReportService reportService;

    /**
     * Listar todos los reportes activos.
     */
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.findAll());
    }

    /**
     * Buscar un reporte por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable int id) {
        Optional<Report> report = reportService.findById(id);
        return report.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Report with ID " + id + " not found"));
    }

    /**
     * Buscar reportes por tipo.
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Report>> getReportsByType(@PathVariable ReportType type) {
        return ResponseEntity.ok(reportService.findByType(type));
    }

    /**
     * Crear nuevo reporte.
     */
    @PostMapping
    public ResponseEntity<?> createReport(@Valid @RequestBody Report report) {
        if (report.getId() != null) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Report ID must be null when creating a new report\"}");
        }
        reportService.save(report);
        return ResponseEntity.ok("{\"message\": \"Report created successfully\"}");
    }

    /**
     * Actualizar reporte existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(@PathVariable int id, @Valid @RequestBody Report report) {
        if (reportService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"Report not found\"}");
        }
        report.setId(id);
        reportService.save(report);
        return ResponseEntity.ok("{\"message\": \"Report updated successfully\"}");
    }

    /**
     * Eliminar reporte (borrado lógico).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable int id) {
        if (reportService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body("{\"message\": \"Report not found\"}");
        }
        reportService.softDelete(id);
        return ResponseEntity.ok("{\"message\": \"Report deleted successfully\"}");
    }
}
