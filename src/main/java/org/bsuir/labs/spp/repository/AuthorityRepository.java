package org.bsuir.labs.spp.repository;

import org.bsuir.labs.spp.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
