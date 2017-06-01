package org.bsuir.labs.spp.repository;

import org.bsuir.labs.spp.domain.Task;
import org.bsuir.labs.spp.domain.TaskRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface TaskRevisionRepository extends JpaRepository<TaskRevision,Long> {

    @Query("select task_revision from TaskRevision task_revision where task_revision.author.login = ?#{principal.username}")
    List<TaskRevision> findByAuthorIsCurrentUser();

    @Query("select task_revision from TaskRevision task_revision where task_revision.assignee.login = ?#{principal.username}")
    List<TaskRevision> findByAssigneeIsCurrentUser();

    @Query("select task_revision from TaskRevision task_revision where task_revision.task.id =:id")
    List<TaskRevision> findByTaskId(@Param("id") Long id);

    @Query("select distinct task_revision from TaskRevision task_revision left join fetch task_revision.watchers left join fetch task_revision.tags")
    List<TaskRevision> findAllWithEagerRelationships();

    @Query("select task_revision from TaskRevision task_revision left join fetch task_revision.watchers left join fetch task_revision.tags where task_revision.id =:id")
    TaskRevision findOneWithEagerRelationships(@Param("id") Long id);
}
