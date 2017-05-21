package org.bsuir.labs.repositories;

import org.bsuir.labs.entities.UsersEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends EntityRepository<UsersEntity> {
    UsersEntity findByUsername(String username);
}
