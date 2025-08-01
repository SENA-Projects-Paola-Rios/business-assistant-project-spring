package com.sena.BusinessAssistantSpring.repository;

import com.sena.BusinessAssistantSpring.model.Report;
import com.sena.BusinessAssistantSpring.model.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    // Obtener todos los reportes no eliminados
    List<Report> findByDeletedAtIsNull();

    // Obtener reportes por tipo
    List<Report> findByReportTypeAndDeletedAtIsNull(ReportType reportType);
}
