package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Server;
import io.github.jhipster.application.repository.ServerRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Server.
 */
@RestController
@RequestMapping("/api")
public class ServerResource {

    private final Logger log = LoggerFactory.getLogger(ServerResource.class);

    private static final String ENTITY_NAME = "server";

    private final ServerRepository serverRepository;

    public ServerResource(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    /**
     * POST  /servers : Create a new server.
     *
     * @param server the server to create
     * @return the ResponseEntity with status 201 (Created) and with body the new server, or with status 400 (Bad Request) if the server has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servers")
    @Timed
    public ResponseEntity<Server> createServer(@Valid @RequestBody Server server) throws URISyntaxException {
        log.debug("REST request to save Server : {}", server);
        if (server.getId() != null) {
            throw new BadRequestAlertException("A new server cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Server result = serverRepository.save(server);
        return ResponseEntity.created(new URI("/api/servers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servers : Updates an existing server.
     *
     * @param server the server to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated server,
     * or with status 400 (Bad Request) if the server is not valid,
     * or with status 500 (Internal Server Error) if the server couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servers")
    @Timed
    public ResponseEntity<Server> updateServer(@Valid @RequestBody Server server) throws URISyntaxException {
        log.debug("REST request to update Server : {}", server);
        if (server.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Server result = serverRepository.save(server);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, server.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servers : get all the servers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of servers in body
     */
    @GetMapping("/servers")
    @Timed
    public List<Server> getAllServers() {
        log.debug("REST request to get all Servers");
        return serverRepository.findAll();
    }

    /**
     * GET  /servers/:id : get the "id" server.
     *
     * @param id the id of the server to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the server, or with status 404 (Not Found)
     */
    @GetMapping("/servers/{id}")
    @Timed
    public ResponseEntity<Server> getServer(@PathVariable Long id) {
        log.debug("REST request to get Server : {}", id);
        Optional<Server> server = serverRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(server);
    }

    /**
     * DELETE  /servers/:id : delete the "id" server.
     *
     * @param id the id of the server to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servers/{id}")
    @Timed
    public ResponseEntity<Void> deleteServer(@PathVariable Long id) {
        log.debug("REST request to delete Server : {}", id);

        serverRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
