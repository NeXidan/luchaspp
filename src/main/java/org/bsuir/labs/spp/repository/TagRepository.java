package org.bsuir.labs.spp.repository;

import org.bsuir.labs.spp.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

}
