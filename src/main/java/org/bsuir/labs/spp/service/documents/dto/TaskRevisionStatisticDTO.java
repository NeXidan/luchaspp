package org.bsuir.labs.spp.service.documents.dto;

import org.bsuir.labs.spp.domain.enumeration.Priority;
import org.bsuir.labs.spp.domain.enumeration.TaskStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class TaskRevisionStatisticDTO {
    private Instant updateAt;
    private String name;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private String sprint;
    private String project;
    private String assignee;

    public TaskRevisionStatisticDTO(){

    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
