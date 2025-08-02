package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.exception.BusinessValidationException;
import com.sena.BusinessAssistantSpring.model.Report;
import com.sena.BusinessAssistantSpring.repository.ReportRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Devuelve todos los reportes activos (no eliminados lógicamente).
     */
    public List<Report> findAll() {
        return reportRepository.findByDeletedAtIsNull();
    }

    /**
     * Busca un reporte por ID solo si no ha sido eliminado.
     */
    public Optional<Report> findById(int id) {
        return reportRepository.findById(id)
                .filter(r -> r.getDeletedAt() == null);
    }

    /**
     * Guarda o actualiza un reporte, validando la unicidad del slug.
     */
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

    /**
     * Elimina un reporte de forma lógica.
     */
    @Transactional
    public boolean softDelete(int id) {
        return reportRepository.findById(id).map(report -> {
            report.setDeletedAt(LocalDateTime.now());
            reportRepository.save(report);
            return true;
        }).orElse(false);
    }

    /**
     * Buscar reporte por slug (usado para generación dinámica de reportes).
     */
    public Optional<Report> findBySlug(String slug) {
        return reportRepository.findByDeletedAtIsNull()
                .stream()
                .filter(r -> r.getSlug().equalsIgnoreCase(slug))
                .findFirst();
    }

    /**
     * Cargar archivo SQL desde resources/sql/
     * Se usa para ejecutar reportes dinámicos definidos por el usuario.
     */
    public String loadSqlFile(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return new String(resource.getInputStream().readAllBytes());
    }

    /**
     * Ejecutar consulta SQL dinámica usando parámetros nombrados de forma segura.
     *
     * Esta implementación utiliza NamedParameterJdbcTemplate,
     * lo que evita vulnerabilidades de inyección SQL al reemplazar de forma segura
     * los parámetros nombrados (ej: :user_id, :total, etc.) por los valores recibidos en la URL.
     */
    public List<Map<String, Object>> executeDynamicQuery(String sql, Map<String, String> params) {
        MapSqlParameterSource namedParams = new MapSqlParameterSource();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            // ✅ Se agregan los parámetros de forma segura para evitar SQL Injection
            namedParams.addValue(entry.getKey(), entry.getValue());
        }

        return jdbcTemplate.queryForList(sql, namedParams);
    }
    
    /**
     * Extrae todos los parámetros nombrados (ej: :user_id) desde un SQL.
     */
    public Set<String> extractNamedParameters(String sql) {
        Set<String> parameters = new HashSet<>();
        Pattern pattern = Pattern.compile(":(\\w+)");
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            parameters.add(matcher.group(1));
        }
        return parameters;
    }

    /**
     * Convierte una lista de resultados (List<Map<String, Object>>) en un CSV.
     *
     * Este método toma el conjunto de resultados de la consulta y lo convierte en
     * una cadena CSV con encabezados dinámicos.
     */
    public String convertToCsv(List<Map<String, Object>> result) throws IOException {
        if (result.isEmpty()) return "";

        StringWriter out = new StringWriter();
        try (CSVPrinter csvPrinter = new CSVPrinter(out,
                CSVFormat.DEFAULT.withHeader(result.get(0).keySet().toArray(new String[0])))) {

            for (Map<String, Object> row : result) {
                csvPrinter.printRecord(row.values());
            }
        }

        return out.toString();
    }
}
