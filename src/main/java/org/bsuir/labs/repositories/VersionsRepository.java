package org.bsuir.labs.repositories;

import org.bsuir.labs.entities.VersionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionsRepository extends JpaRepository<VersionsEntity, Integer> {
    
}
