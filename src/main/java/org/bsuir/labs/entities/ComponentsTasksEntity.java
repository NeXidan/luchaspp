package org.bsuir.labs.entities;

import javax.persistence.*;

@Table(name = "components_tasks")
public class ComponentsTasksEntity {

    private ComponentsEntity componentsEntity;
    private TasksEntity tasksEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "component_id")
    public ComponentsEntity getComponent() {
        return componentsEntity;
    }

    public void setComponent(ComponentsEntity entity) {
        this.componentsEntity = entity;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "component_id")
    public TasksEntity getTask() {
        return tasksEntity;
    }

    public void setTask(TasksEntity entity) {
        this.tasksEntity = entity;
    }

}
