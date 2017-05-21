package org.bsuir.labs.entities;

import javax.persistence.*;

@Table(name = "task_watchers")
public class TaskWatchersEntity {
    private TasksEntity taskEntity;
    private UsersEntity userEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "task_id")
    public TasksEntity getTask() {
        return taskEntity;
    }

    public void setTask(TasksEntity entity) {
        this.taskEntity = entity;
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
