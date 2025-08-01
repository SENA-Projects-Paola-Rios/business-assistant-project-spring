package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.exception.BusinessValidationException;
import com.sena.BusinessAssistantSpring.model.Report;
import com.sena.BusinessAssistantSpring.repository.ReportRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    // Devuelve todos los reportes activos (no eliminados lógicamente)
    public List<Report> findAll() {
        return reportRepository.findByDeletedAtIsNull();
    }

    // Busca un reporte por ID solo si no ha sido eliminado
    public Optional<Report> findById(int id) {
        return reportRepository.findById(id)
                .filter(r -> r.getDeletedAt() == null);
    }

    // Guarda o actualiza un reporte, validando unicidad de slug
    public Report save(Report report) {
        List<ObjectError> businessErrors = new ArrayList<>();

        if (reportRepository.existsBySlugAndIdNot(
                report.getSlug(),
                report.getId() == null ? -1 : report.getId())
        ) {
            businessErrors.add(new ObjectError("slug", "The slug is already in use"));
        }

        if (!businessErrors.isEmpty()) {
            throw new BusinessValidationException(businessErrors);
        }

        return reportRepository.save(report);
    }

    // Elimina un reporte de forma lógica
    @Transactional
    public boolean softDelete(int id) {
        return reportRepository.findById(id).map(report -> {
            report.setDeletedAt(LocalDateTime.now());
            reportRepository.save(report);
            return true;
        }).orElse(false);
    }
}
