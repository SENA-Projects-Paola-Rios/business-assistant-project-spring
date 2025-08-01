package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.model.Report;
import com.sena.BusinessAssistantSpring.model.ReportType;
import com.sena.BusinessAssistantSpring.repository.ReportRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    // Listado de reportes activos
    public List<Report> findAll() {
        return reportRepository.findByDeletedAtIsNull();
    }

    // Buscar reporte por ID, si no está eliminado
    public Optional<Report> findById(int id) {
        return reportRepository.findById(id)
                .filter(r -> r.getDeletedAt() == null);
    }

    // Guardar o actualizar sin validaciones adicionales (podrías agregar si lo necesitas)
    public Report save(Report report) {
        return reportRepository.save(report);
    }

    // Borrado lógico
    @Transactional
    public boolean softDelete(int id) {
        return reportRepository.findById(id).map(report -> {
            report.setDeletedAt(LocalDateTime.now());
            reportRepository.save(report);
            return true;
        }).orElse(false);
    }

    // Buscar por tipo de reporte
    public List<Report> findByType(ReportType type) {
        return reportRepository.findByReportTypeAndDeletedAtIsNull(type);
    }
}
