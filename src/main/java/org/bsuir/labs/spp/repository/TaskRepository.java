package org.bsuir.labs.spp.repository;

import org.bsuir.labs.spp.domain.Sprint;
import org.bsuir.labs.spp.domain.Task;
import org.bsuir.labs.spp.domain.TaskRevision;
import org.bsuir.labs.spp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("select task from Task task where task.author.login = ?#{principal.username}")
    List<Task> findByAuthorIsCurrentUser();

    @Query("select task from Task task where task.assignee.login = ?#{principal.username}")
    List<Task> findByAssigneeIsCurrentUser();

    @Query("select distinct task from Task task left join fetch task.watchers left join fetch task.tags")
    List<Task> findAllWithEagerRelationships();

    @Query("select task from Task task left join fetch task.watchers left join fetch task.tags where task.id =:id")
    Task findOneWithEagerRelationships(@Param("id") Long id);

    List<Task> findByAssignee(User user);

    List<Task> findBySprint(Sprint sprint);

    List<Task> findByProjectId(Long id);
}
