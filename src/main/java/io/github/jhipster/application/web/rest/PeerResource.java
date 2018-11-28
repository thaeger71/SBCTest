package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Peer;
import io.github.jhipster.application.repository.PeerRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Peer.
 */
@RestController
@RequestMapping("/api")
public class PeerResource {

    private final Logger log = LoggerFactory.getLogger(PeerResource.class);

    private static final String ENTITY_NAME = "peer";

    private final PeerRepository peerRepository;

    public PeerResource(PeerRepository peerRepository) {
        this.peerRepository = peerRepository;
    }

    /**
     * POST  /peers : Create a new peer.
     *
     * @param peer the peer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new peer, or with status 400 (Bad Request) if the peer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/peers")
    @Timed
    public ResponseEntity<Peer> createPeer(@RequestBody Peer peer) throws URISyntaxException {
        log.debug("REST request to save Peer : {}", peer);
        if (peer.getId() != null) {
            throw new BadRequestAlertException("A new peer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Peer result = peerRepository.save(peer);
        return ResponseEntity.created(new URI("/api/peers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /peers : Updates an existing peer.
     *
     * @param peer the peer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated peer,
     * or with status 400 (Bad Request) if the peer is not valid,
     * or with status 500 (Internal Server Error) if the peer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/peers")
    @Timed
    public ResponseEntity<Peer> updatePeer(@RequestBody Peer peer) throws URISyntaxException {
        log.debug("REST request to update Peer : {}", peer);
        if (peer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Peer result = peerRepository.save(peer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, peer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /peers : get all the peers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of peers in body
     */
    @GetMapping("/peers")
    @Timed
    public List<Peer> getAllPeers() {
        log.debug("REST request to get all Peers");
        return peerRepository.findAll();
    }

    /**
     * GET  /peers/:id : get the "id" peer.
     *
     * @param id the id of the peer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the peer, or with status 404 (Not Found)
     */
    @GetMapping("/peers/{id}")
    @Timed
    public ResponseEntity<Peer> getPeer(@PathVariable Long id) {
        log.debug("REST request to get Peer : {}", id);
        Optional<Peer> peer = peerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(peer);
    }

    /**
     * DELETE  /peers/:id : delete the "id" peer.
     *
     * @param id the id of the peer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/peers/{id}")
    @Timed
    public ResponseEntity<Void> deletePeer(@PathVariable Long id) {
        log.debug("REST request to delete Peer : {}", id);

        peerRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
