package org.bsuir.labs.spp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @ManyToOne
    private Project project;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<TaskRevision> taskRevisions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tag name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public Tag project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Tag tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Tag addTask(Task task) {
        this.tasks.add(task);
        task.getTags().add(this);
        return this;
    }

    public Tag removeTask(Task task) {
        this.tasks.remove(task);
        task.getTags().remove(this);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<TaskRevision> getTaskRevisions() {
        return taskRevisions;
    }

    public Tag taskRevisions(Set<TaskRevision> taskRevisions) {
        this.taskRevisions = taskRevisions;
        return this;
    }

    public Tag addTaskRevision(TaskRevision taskRevision) {
        this.taskRevisions.add(taskRevision);
        taskRevision.getTags().add(this);
        return this;
    }

    public Tag removeTaskRevision(TaskRevision taskRevision) {
        this.taskRevisions.remove(taskRevision);
        taskRevision.getTags().remove(this);
        return this;
    }

    public void setTaskRevisions(Set<TaskRevision> taskRevisions) {
        this.taskRevisions = taskRevisions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        if (tag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
