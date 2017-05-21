package org.bsuir.labs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository<T> extends JpaRepository<T, Integer> {

}
