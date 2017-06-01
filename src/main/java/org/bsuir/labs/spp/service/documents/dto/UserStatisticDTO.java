package org.bsuir.labs.spp.service.documents.dto;

public class UserStatisticDTO {
    private String fullName;
    private Integer openTasks;
    private Integer progressTasks;
    private Integer completedTasks;
    private Long id;
    public UserStatisticDTO(){

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOpenTasks() {
        return openTasks;
    }

    public void setOpenTasks(Integer openTasks) {
        this.openTasks = openTasks;
    }

    public Integer getProgressTasks() {
        return progressTasks;
    }

    public void setProgressTasks(Integer progressTasks) {
        this.progressTasks = progressTasks;
    }

    public Integer getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(Integer completedTasks) {
        this.completedTasks = completedTasks;
    }
}
