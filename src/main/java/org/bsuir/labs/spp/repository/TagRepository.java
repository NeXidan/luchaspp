package org.bsuir.labs.spp.repository;

import org.bsuir.labs.spp.domain.Sprint;
import org.bsuir.labs.spp.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    @Query("select tag from Tag tag left join fetch tag.project project left join fetch project.managers where tag.id =:id")
    Tag findOneWithEagerRelationships(@Param("id") Long id);

    List<Tag> findAllByProjectId(Long id);
}
