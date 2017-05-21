package org.bsuir.labs.repositories;

import org.bsuir.labs.entities.TasksEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends EntityRepository<TasksEntity> {
    
}
