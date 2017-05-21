package org.bsuir.labs.repositories;

import org.bsuir.labs.entities.SprintsEntity;
import org.bsuir.labs.entities.TasksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends JpaRepository<TasksEntity, Integer> {
    
}
