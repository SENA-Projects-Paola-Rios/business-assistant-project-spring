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

    // Nombre del reporte
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

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

    // Campos seleccionados del reporte
    @Size(max = 1000, message = "Fields must be at most 1000 characters")
    @Column(name = "fields", length = 1000)
    private String fields;

    // Campo para borrado lógico
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Constructores
    public Report() {}

    public Report(String name, ReportType reportType, String fileName, String slug, String fields) {
        this.name = name;
        this.reportType = reportType;
        this.fileName = fileName;
        this.slug = slug;
        this.fields = fields;
    }

    public Report(Integer id, String name, ReportType reportType, String fileName, String slug, String fields, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.reportType = reportType;
        this.fileName = fileName;
        this.slug = slug;
        this.fields = fields;
        this.deletedAt = deletedAt;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
