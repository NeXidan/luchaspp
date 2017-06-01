package org.bsuir.labs.spp.repository;

import org.bsuir.labs.spp.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("select distinct project from Project project left join fetch project.managers")
    List<Project> findAllWithEagerRelationships();

    @Query("select project from Project project left join fetch project.managers where project.id =:id")
    Project findOneWithEagerRelationships(@Param("id") Long id);

}
