package org.bsuir.labs.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class VersionsEntityPK implements Serializable {
    private int id;
    private int projectId;

    @Column(name = "id", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "project_id", nullable = false)
    @Id
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

        VersionsEntityPK that = (VersionsEntityPK) o;

        if (id != that.id) return false;
        if (projectId != that.projectId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + projectId;
        return result;
    }
}
