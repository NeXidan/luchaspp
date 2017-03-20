package org.bsuir.labs.repositories;

import org.bsuir.labs.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    public ProjectEntity findByName(String name);

}
