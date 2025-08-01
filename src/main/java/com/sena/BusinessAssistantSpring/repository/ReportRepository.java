package com.sena.BusinessAssistantSpring.repository;

import com.sena.BusinessAssistantSpring.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    // Devuelve solo los reportes que no han sido eliminados lógicamente
    List<Report> findByDeletedAtIsNull();

    // Verifica si existe un reporte con el mismo slug
    boolean existsBySlug(String slug);

    // Verifica si existe un reporte con el mismo slug pero con ID diferente (para validación al editar)
    boolean existsBySlugAndIdNot(String slug, Integer id);
}
