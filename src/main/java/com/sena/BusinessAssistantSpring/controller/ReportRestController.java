package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.exception.ResourceNotFoundException;
import com.sena.BusinessAssistantSpring.model.Report;
import com.sena.BusinessAssistantSpring.service.ReportService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controlador REST para la gestión de reportes.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    @Autowired
    private ReportService reportService;

    /**
     * Obtener todos los reportes activos (borrado lógico en NULL).
     */
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.findAll());
    }

    /**
     * Obtener un reporte por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable int id) {
        return reportService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Report with ID " + id + " not found"));
    }

    /**
     * Crear un nuevo reporte.
     */
    @PostMapping
    public ResponseEntity<?> createReport(@Valid @RequestBody Report report) {
        if (report.getId() != null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Report ID must be null when creating a new report"));
        }
        reportService.save(report);
        return ResponseEntity.ok(Map.of("message", "Report created successfully"));
    }

    /**
     * Actualizar un reporte existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(@PathVariable int id, @Valid @RequestBody Report report) {
        if (reportService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Report not found"));
        }
        report.setId(id);
        reportService.save(report);
        return ResponseEntity.ok(Map.of("message", "Report updated successfully"));
    }

    /**
     * Eliminar un reporte (borrado lógico colocando fecha actual en deleted_at).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable int id) {
        if (reportService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Report not found"));
        }
        reportService.softDelete(id);
        return ResponseEntity.ok(Map.of("message", "Report deleted successfully"));
    }

    /**
     * Generar un reporte CSV usando slug + parámetros dinámicos.
     * Ejemplo: /api/reports/generate/sales?user_id=1&total=100
     */
    /**
     * Generar un reporte CSV usando slug + parámetros dinámicos.
     */
    @GetMapping("/generate/{slug}")
    public ResponseEntity<?> generateReport(
            @PathVariable String slug,
            @RequestParam Map<String, String> queryParams
    ) {
        Optional<Report> reportOpt = reportService.findBySlug(slug);
        if (reportOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Report not found"));
        }

        Report report = reportOpt.get();
        String fileName = report.getFileName(); // Ej: "sales.sql"

        try {
            // Cargar SQL
            String sql = reportService.loadSqlFile("sql/" + fileName);

            // Validar que todos los parámetros nombrados existan en la URL
            Set<String> requiredParams = reportService.extractNamedParameters(sql);
            List<String> missing = requiredParams.stream()
                    .filter(p -> !queryParams.containsKey(p))
                    .toList();

            if (!missing.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Missing required parameters: " + String.join(", ", missing)));
            }

            // Ejecutar consulta
            List<Map<String, Object>> result = reportService.executeDynamicQuery(sql, queryParams);

            // Convertir a CSV
            String csv = reportService.convertToCsv(result);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + slug + ".csv\"")
                    .header("Content-Type", "text/csv; charset=UTF-8")
                    .body(csv);

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error generating report: " + e.getMessage()));
        }
    }

}
