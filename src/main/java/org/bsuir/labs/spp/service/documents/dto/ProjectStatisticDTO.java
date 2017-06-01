package org.bsuir.labs.spp.service.documents.dto;

import org.bsuir.labs.spp.domain.enumeration.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public class ProjectStatisticDTO {
    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
    private List<String> managers;
    public ProjectStatisticDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public List<String> getManagers() {
        return managers;
    }

    public void setManagers(List<String> managers) {
        this.managers = managers;
    }
}
