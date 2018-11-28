package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SbcTestApp;

import io.github.jhipster.application.domain.DialplanEntry;
import io.github.jhipster.application.domain.Peer;
import io.github.jhipster.application.repository.DialplanEntryRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.MatchType;
/**
 * Test class for the DialplanEntryResource REST controller.
 *
 * @see DialplanEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbcTestApp.class)
public class DialplanEntryResourceIntTest {

    private static final MatchType DEFAULT_FROM_PEER_MATCH_TYPE = MatchType.IP;
    private static final MatchType UPDATED_FROM_PEER_MATCH_TYPE = MatchType.FROM_USER;

    private static final String DEFAULT_FROM_PEER_MATCH_EXP = "AAAAAAAAAA";
    private static final String UPDATED_FROM_PEER_MATCH_EXP = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_NEW_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_NEW_SOURCE = "BBBBBBBBBB";

    @Autowired
    private DialplanEntryRepository dialplanEntryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDialplanEntryMockMvc;

    private DialplanEntry dialplanEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DialplanEntryResource dialplanEntryResource = new DialplanEntryResource(dialplanEntryRepository);
        this.restDialplanEntryMockMvc = MockMvcBuilders.standaloneSetup(dialplanEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DialplanEntry createEntity(EntityManager em) {
        DialplanEntry dialplanEntry = new DialplanEntry()
            .fromPeerMatchType(DEFAULT_FROM_PEER_MATCH_TYPE)
            .fromPeerMatchExp(DEFAULT_FROM_PEER_MATCH_EXP)
            .destination(DEFAULT_DESTINATION)
            .newDestination(DEFAULT_NEW_DESTINATION)
            .source(DEFAULT_SOURCE)
            .newSource(DEFAULT_NEW_SOURCE);
        // Add required entity
        Peer peer = PeerResourceIntTest.createEntity(em);
        em.persist(peer);
        em.flush();
        dialplanEntry.setFromPeer(peer);
        return dialplanEntry;
    }

    @Before
    public void initTest() {
        dialplanEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createDialplanEntry() throws Exception {
        int databaseSizeBeforeCreate = dialplanEntryRepository.findAll().size();

        // Create the DialplanEntry
        restDialplanEntryMockMvc.perform(post("/api/dialplan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialplanEntry)))
            .andExpect(status().isCreated());

        // Validate the DialplanEntry in the database
        List<DialplanEntry> dialplanEntryList = dialplanEntryRepository.findAll();
        assertThat(dialplanEntryList).hasSize(databaseSizeBeforeCreate + 1);
        DialplanEntry testDialplanEntry = dialplanEntryList.get(dialplanEntryList.size() - 1);
        assertThat(testDialplanEntry.getFromPeerMatchType()).isEqualTo(DEFAULT_FROM_PEER_MATCH_TYPE);
        assertThat(testDialplanEntry.getFromPeerMatchExp()).isEqualTo(DEFAULT_FROM_PEER_MATCH_EXP);
        assertThat(testDialplanEntry.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testDialplanEntry.getNewDestination()).isEqualTo(DEFAULT_NEW_DESTINATION);
        assertThat(testDialplanEntry.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testDialplanEntry.getNewSource()).isEqualTo(DEFAULT_NEW_SOURCE);
    }

    @Test
    @Transactional
    public void createDialplanEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dialplanEntryRepository.findAll().size();

        // Create the DialplanEntry with an existing ID
        dialplanEntry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDialplanEntryMockMvc.perform(post("/api/dialplan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialplanEntry)))
            .andExpect(status().isBadRequest());

        // Validate the DialplanEntry in the database
        List<DialplanEntry> dialplanEntryList = dialplanEntryRepository.findAll();
        assertThat(dialplanEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFromPeerMatchTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dialplanEntryRepository.findAll().size();
        // set the field null
        dialplanEntry.setFromPeerMatchType(null);

        // Create the DialplanEntry, which fails.

        restDialplanEntryMockMvc.perform(post("/api/dialplan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialplanEntry)))
            .andExpect(status().isBadRequest());

        List<DialplanEntry> dialplanEntryList = dialplanEntryRepository.findAll();
        assertThat(dialplanEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDestinationIsRequired() throws Exception {
        int databaseSizeBeforeTest = dialplanEntryRepository.findAll().size();
        // set the field null
        dialplanEntry.setDestination(null);

        // Create the DialplanEntry, which fails.

        restDialplanEntryMockMvc.perform(post("/api/dialplan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialplanEntry)))
            .andExpect(status().isBadRequest());

        List<DialplanEntry> dialplanEntryList = dialplanEntryRepository.findAll();
        assertThat(dialplanEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNewDestinationIsRequired() throws Exception {
        int databaseSizeBeforeTest = dialplanEntryRepository.findAll().size();
        // set the field null
        dialplanEntry.setNewDestination(null);

        // Create the DialplanEntry, which fails.

        restDialplanEntryMockMvc.perform(post("/api/dialplan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialplanEntry)))
            .andExpect(status().isBadRequest());

        List<DialplanEntry> dialplanEntryList = dialplanEntryRepository.findAll();
        assertThat(dialplanEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = dialplanEntryRepository.findAll().size();
        // set the field null
        dialplanEntry.setSource(null);

        // Create the DialplanEntry, which fails.

        restDialplanEntryMockMvc.perform(post("/api/dialplan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialplanEntry)))
            .andExpect(status().isBadRequest());

        List<DialplanEntry> dialplanEntryList = dialplanEntryRepository.findAll();
        assertThat(dialplanEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNewSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = dialplanEntryRepository.findAll().size();
        // set the field null
        dialplanEntry.setNewSource(null);

        // Create the DialplanEntry, which fails.

        restDialplanEntryMockMvc.perform(post("/api/dialplan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialplanEntry)))
            .andExpect(status().isBadRequest());

        List<DialplanEntry> dialplanEntryList = dialplanEntryRepository.findAll();
        assertThat(dialplanEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDialplanEntries() throws Exception {
        // Initialize the database
        dialplanEntryRepository.saveAndFlush(dialplanEntry);

        // Get all the dialplanEntryList
        restDialplanEntryMockMvc.perform(get("/api/dialplan-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dialplanEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromPeerMatchType").value(hasItem(DEFAULT_FROM_PEER_MATCH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fromPeerMatchExp").value(hasItem(DEFAULT_FROM_PEER_MATCH_EXP.toString())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].newDestination").value(hasItem(DEFAULT_NEW_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].newSource").value(hasItem(DEFAULT_NEW_SOURCE.toString())));
    }
    
    @Test
    @Transactional
    public void getDialplanEntry() throws Exception {
        // Initialize the database
        dialplanEntryRepository.saveAndFlush(dialplanEntry);

        // Get the dialplanEntry
        restDialplanEntryMockMvc.perform(get("/api/dialplan-entries/{id}", dialplanEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dialplanEntry.getId().intValue()))
            .andExpect(jsonPath("$.fromPeerMatchType").value(DEFAULT_FROM_PEER_MATCH_TYPE.toString()))
            .andExpect(jsonPath("$.fromPeerMatchExp").value(DEFAULT_FROM_PEER_MATCH_EXP.toString()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()))
            .andExpect(jsonPath("$.newDestination").value(DEFAULT_NEW_DESTINATION.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.newSource").value(DEFAULT_NEW_SOURCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDialplanEntry() throws Exception {
        // Get the dialplanEntry
        restDialplanEntryMockMvc.perform(get("/api/dialplan-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDialplanEntry() throws Exception {
        // Initialize the database
        dialplanEntryRepository.saveAndFlush(dialplanEntry);

        int databaseSizeBeforeUpdate = dialplanEntryRepository.findAll().size();

        // Update the dialplanEntry
        DialplanEntry updatedDialplanEntry = dialplanEntryRepository.findById(dialplanEntry.getId()).get();
        // Disconnect from session so that the updates on updatedDialplanEntry are not directly saved in db
        em.detach(updatedDialplanEntry);
        updatedDialplanEntry
            .fromPeerMatchType(UPDATED_FROM_PEER_MATCH_TYPE)
            .fromPeerMatchExp(UPDATED_FROM_PEER_MATCH_EXP)
            .destination(UPDATED_DESTINATION)
            .newDestination(UPDATED_NEW_DESTINATION)
            .source(UPDATED_SOURCE)
            .newSource(UPDATED_NEW_SOURCE);

        restDialplanEntryMockMvc.perform(put("/api/dialplan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDialplanEntry)))
            .andExpect(status().isOk());

        // Validate the DialplanEntry in the database
        List<DialplanEntry> dialplanEntryList = dialplanEntryRepository.findAll();
        assertThat(dialplanEntryList).hasSize(databaseSizeBeforeUpdate);
        DialplanEntry testDialplanEntry = dialplanEntryList.get(dialplanEntryList.size() - 1);
        assertThat(testDialplanEntry.getFromPeerMatchType()).isEqualTo(UPDATED_FROM_PEER_MATCH_TYPE);
        assertThat(testDialplanEntry.getFromPeerMatchExp()).isEqualTo(UPDATED_FROM_PEER_MATCH_EXP);
        assertThat(testDialplanEntry.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testDialplanEntry.getNewDestination()).isEqualTo(UPDATED_NEW_DESTINATION);
        assertThat(testDialplanEntry.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testDialplanEntry.getNewSource()).isEqualTo(UPDATED_NEW_SOURCE);
    }

    @Test
    @Transactional
    public void updateNonExistingDialplanEntry() throws Exception {
        int databaseSizeBeforeUpdate = dialplanEntryRepository.findAll().size();

        // Create the DialplanEntry

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDialplanEntryMockMvc.perform(put("/api/dialplan-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialplanEntry)))
            .andExpect(status().isBadRequest());

        // Validate the DialplanEntry in the database
        List<DialplanEntry> dialplanEntryList = dialplanEntryRepository.findAll();
        assertThat(dialplanEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDialplanEntry() throws Exception {
        // Initialize the database
        dialplanEntryRepository.saveAndFlush(dialplanEntry);

        int databaseSizeBeforeDelete = dialplanEntryRepository.findAll().size();

        // Get the dialplanEntry
        restDialplanEntryMockMvc.perform(delete("/api/dialplan-entries/{id}", dialplanEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DialplanEntry> dialplanEntryList = dialplanEntryRepository.findAll();
        assertThat(dialplanEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DialplanEntry.class);
        DialplanEntry dialplanEntry1 = new DialplanEntry();
        dialplanEntry1.setId(1L);
        DialplanEntry dialplanEntry2 = new DialplanEntry();
        dialplanEntry2.setId(dialplanEntry1.getId());
        assertThat(dialplanEntry1).isEqualTo(dialplanEntry2);
        dialplanEntry2.setId(2L);
        assertThat(dialplanEntry1).isNotEqualTo(dialplanEntry2);
        dialplanEntry1.setId(null);
        assertThat(dialplanEntry1).isNotEqualTo(dialplanEntry2);
    }
}
