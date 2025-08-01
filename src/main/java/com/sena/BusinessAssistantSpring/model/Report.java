package com.sena.BusinessAssistantSpring.model;

import com.sena.BusinessAssistantSpring.model.ReportType;
import com.sena.BusinessAssistantSpring.model.validation.Create;
import com.sena.BusinessAssistantSpring.model.validation.Update;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = Create.class, message = "ID must be null when creating a new report")
    @NotNull(groups = Update.class, message = "ID is required when updating")
    private Integer id;

    // Tipo de reporte: puede ser sales, inventory, products o alerts
    @NotNull(message = "Report type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    // Nombre del archivo generado
    @NotBlank(message = "File name is required")
    @Size(max = 100, message = "File name must be at most 100 characters")
    @Column(name = "file_name", nullable = false, length = 100)
    private String fileName;

    // Slug único del reporte (para uso en rutas o URLs)
    @NotBlank(message = "Slug is required")
    @Size(max = 100, message = "Slug must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String slug;

    // Campo para borrado lógico
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Constructores
    public Report() {}

    public Report(ReportType reportType, String fileName, String slug) {
        this.reportType = reportType;
        this.fileName = fileName;
        this.slug = slug;
    }

    public Report(Integer id, ReportType reportType, String fileName, String slug, LocalDateTime deletedAt) {
        this.id = id;
        this.reportType = reportType;
        this.fileName = fileName;
        this.slug = slug;
        this.deletedAt = deletedAt;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
