package org.bsuir.labs.spp.repository;

import org.bsuir.labs.spp.domain.Sprint;
import org.bsuir.labs.spp.domain.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {
    @Query("select sprint from Sprint sprint left join fetch sprint.project project left join fetch project.managers where sprint.id =:id")
    Sprint findOneWithEagerRelationships(@Param("id") Long id);

    List<Sprint> findAll();

    List<Sprint> findAllByProjectId(Long projectId);
}
