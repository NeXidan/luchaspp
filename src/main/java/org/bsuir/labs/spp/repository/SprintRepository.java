package org.bsuir.labs.spp.repository;

import org.bsuir.labs.spp.domain.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {
    List<Sprint> findAll();
}
