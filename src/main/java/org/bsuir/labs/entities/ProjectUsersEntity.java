package org.bsuir.labs.entities;

import javax.persistence.*;

@Table(name = "project_users")
public class ProjectUsersEntity {
    private ProjectEntity projectEntity;
    private UsersEntity userEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    public ProjectEntity getProject() {
        return projectEntity;
    }

    public void setProject(ProjectEntity entity) {
        this.projectEntity = entity;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    public UsersEntity getUser() {
        return userEntity;
    }

    public void setUser(UsersEntity entity) {
        this.userEntity = entity;
    }
}
