package com.sena.BusinessAssistantSpring.model;

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

    @NotNull(message = "Report type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Report() {}

    public Report(ReportType reportType) {
        this.reportType = reportType;
    }

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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
