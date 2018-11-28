package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Server;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Server entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {

}
