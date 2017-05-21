package org.bsuir.labs.repositories;

import org.bsuir.labs.entities.ProjectsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectsEntity, Integer> {

    public ProjectsEntity findByName(String name);

    public boolean existsByName(String name);

}
