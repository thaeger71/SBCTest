package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Peer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Peer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeerRepository extends JpaRepository<Peer, Long> {

}
