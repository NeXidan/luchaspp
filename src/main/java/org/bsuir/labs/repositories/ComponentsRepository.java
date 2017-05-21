package org.bsuir.labs.repositories;

import org.bsuir.labs.entities.ComponentsEntity;
import org.bsuir.labs.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentsRepository extends JpaRepository<ComponentsEntity, Integer> {
    public boolean existsByName(String name);
}
