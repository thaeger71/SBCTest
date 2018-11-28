package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DialplanEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DialplanEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DialplanEntryRepository extends JpaRepository<DialplanEntry, Long> {

}
