package org.bsuir.labs.entities;

import javax.persistence.*;

@Table(name = "project_users")
public class ProjectUsersEntity {
    private ProjectsEntity projectEntity;
    private UsersEntity userEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    public ProjectsEntity getProject() {
        return projectEntity;
    }

    public void setProject(ProjectsEntity entity) {
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
