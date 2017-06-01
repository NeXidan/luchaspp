package org.bsuir.labs.spp.domain;


import org.bsuir.labs.spp.domain.enumeration.Priority;
import org.bsuir.labs.spp.domain.enumeration.TaskStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "task_revision")
public class TaskRevision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();

    @ManyToOne
    private Task task;

    @ManyToOne
    private Task parentTask;

    @ManyToOne
    private Sprint sprint;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User author;

    @ManyToOne
    private User assignee;

    @ManyToMany
    @JoinTable(name = "task_revision_watcher",
               joinColumns = @JoinColumn(name="task_revisions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="watchers_id", referencedColumnName="id"))
    private Set<User> watchers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "task_revision_tag",
               joinColumns = @JoinColumn(name="task_revisions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"))
    private Set<Tag> tags = new HashSet<>();

    public TaskRevision(Task task) {
        this.task = task;

        this.name = task.getName();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.priority = task.getPriority();
        this.createdAt = task.getCreatedAt();
        this.parentTask = task.getParentTask();
        this.sprint = task.getSprint();
        this.project = task.getProject();
        this.author = task.getAuthor();
        this.assignee = task.getAssignee();
        this.watchers = new HashSet<>(task.getWatchers());
        this.tags = new HashSet<>(task.getTags());
    }

    public TaskRevision() {

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

    public TaskRevision name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TaskRevision description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskRevision status(TaskStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public TaskRevision priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public TaskRevision createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public TaskRevision updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Task getTask() {
        return task;
    }

    public TaskRevision task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public TaskRevision parentTask(Task task) {
        this.parentTask = task;
        return this;
    }

    public void setParentTask(Task task) {
        this.parentTask = task;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public TaskRevision sprint(Sprint sprint) {
        this.sprint = sprint;
        return this;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Project getProject() {
        return project;
    }

    public TaskRevision project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAuthor() {
        return author;
    }

    public TaskRevision author(User user) {
        this.author = user;
        return this;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public User getAssignee() {
        return assignee;
    }

    public TaskRevision assignee(User user) {
        this.assignee = user;
        return this;
    }

    public void setAssignee(User user) {
        this.assignee = user;
    }

    public Set<User> getWatchers() {
        return watchers;
    }

    public TaskRevision watchers(Set<User> users) {
        this.watchers = users;
        return this;
    }

    public TaskRevision addWatcher(User user) {
        this.watchers.add(user);
        return this;
    }

    public TaskRevision removeWatcher(User user) {
        this.watchers.remove(user);
        return this;
    }

    public void setWatchers(Set<User> users) {
        this.watchers = users;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public TaskRevision tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public TaskRevision addTag(Tag tag) {
        this.tags.add(tag);
        tag.getTaskRevisions().add(this);
        return this;
    }

    public TaskRevision removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getTaskRevisions().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskRevision taskRevision = (TaskRevision) o;
        if (taskRevision.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskRevision.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskRevision{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", priority='" + getPriority() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
