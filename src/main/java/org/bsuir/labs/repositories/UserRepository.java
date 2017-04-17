package org.bsuir.labs.repositories;

import org.bsuir.labs.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Integer> {

    public UsersEntity findByUsername(String username);

}
