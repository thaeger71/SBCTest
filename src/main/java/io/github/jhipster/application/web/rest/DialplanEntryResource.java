package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.DialplanEntry;
import io.github.jhipster.application.repository.DialplanEntryRepository;
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
 * REST controller for managing DialplanEntry.
 */
@RestController
@RequestMapping("/api")
public class DialplanEntryResource {

    private final Logger log = LoggerFactory.getLogger(DialplanEntryResource.class);

    private static final String ENTITY_NAME = "dialplanEntry";

    private final DialplanEntryRepository dialplanEntryRepository;

    public DialplanEntryResource(DialplanEntryRepository dialplanEntryRepository) {
        this.dialplanEntryRepository = dialplanEntryRepository;
    }

    /**
     * POST  /dialplan-entries : Create a new dialplanEntry.
     *
     * @param dialplanEntry the dialplanEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dialplanEntry, or with status 400 (Bad Request) if the dialplanEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dialplan-entries")
    @Timed
    public ResponseEntity<DialplanEntry> createDialplanEntry(@Valid @RequestBody DialplanEntry dialplanEntry) throws URISyntaxException {
        log.debug("REST request to save DialplanEntry : {}", dialplanEntry);
        if (dialplanEntry.getId() != null) {
            throw new BadRequestAlertException("A new dialplanEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DialplanEntry result = dialplanEntryRepository.save(dialplanEntry);
        return ResponseEntity.created(new URI("/api/dialplan-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dialplan-entries : Updates an existing dialplanEntry.
     *
     * @param dialplanEntry the dialplanEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dialplanEntry,
     * or with status 400 (Bad Request) if the dialplanEntry is not valid,
     * or with status 500 (Internal Server Error) if the dialplanEntry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dialplan-entries")
    @Timed
    public ResponseEntity<DialplanEntry> updateDialplanEntry(@Valid @RequestBody DialplanEntry dialplanEntry) throws URISyntaxException {
        log.debug("REST request to update DialplanEntry : {}", dialplanEntry);
        if (dialplanEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DialplanEntry result = dialplanEntryRepository.save(dialplanEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dialplanEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dialplan-entries : get all the dialplanEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dialplanEntries in body
     */
    @GetMapping("/dialplan-entries")
    @Timed
    public List<DialplanEntry> getAllDialplanEntries() {
        log.debug("REST request to get all DialplanEntries");
        return dialplanEntryRepository.findAll();
    }

    /**
     * GET  /dialplan-entries/:id : get the "id" dialplanEntry.
     *
     * @param id the id of the dialplanEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dialplanEntry, or with status 404 (Not Found)
     */
    @GetMapping("/dialplan-entries/{id}")
    @Timed
    public ResponseEntity<DialplanEntry> getDialplanEntry(@PathVariable Long id) {
        log.debug("REST request to get DialplanEntry : {}", id);
        Optional<DialplanEntry> dialplanEntry = dialplanEntryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dialplanEntry);
    }

    /**
     * DELETE  /dialplan-entries/:id : delete the "id" dialplanEntry.
     *
     * @param id the id of the dialplanEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dialplan-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteDialplanEntry(@PathVariable Long id) {
        log.debug("REST request to delete DialplanEntry : {}", id);

        dialplanEntryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
