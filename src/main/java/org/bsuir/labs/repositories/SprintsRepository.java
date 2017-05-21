package org.bsuir.labs.repositories;

import org.bsuir.labs.entities.RolesEntity;
import org.bsuir.labs.entities.SprintsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintsRepository extends JpaRepository<SprintsEntity, Integer> {
    
}
