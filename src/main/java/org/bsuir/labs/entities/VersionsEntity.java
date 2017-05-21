package org.bsuir.labs.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "versions")
@IdClass(VersionsEntityPK.class)
public class VersionsEntity {
    private int id;
    private String name;
    private Timestamp createdAt;
    private int projectId;
    private Set<ProjectsEntity> projects = new HashSet<>(0);

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "created_at", nullable = true)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Id
    @Column(name = "project_id", nullable = false)
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VersionsEntity that = (VersionsEntity) o;

        if (id != that.id) return false;
        if (projectId != that.projectId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + projectId;
        return result;
    }

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name="projects", joinColumns = {@JoinColumn(name="id")}, inverseJoinColumns = {@JoinColumn(name="id")})
//    public Set<ProjectsEntity> getProjects() {
//        return this.projects;
//    }
//
//    public void setProjects(Set<ProjectsEntity> projects) {
//        this.projects = projects;
//    }

}
