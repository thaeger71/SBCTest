package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SbcTestApp;

import io.github.jhipster.application.domain.Peer;
import io.github.jhipster.application.domain.Server;
import io.github.jhipster.application.repository.PeerRepository;
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

import io.github.jhipster.application.domain.enumeration.Transport;
/**
 * Test class for the PeerResource REST controller.
 *
 * @see PeerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbcTestApp.class)
public class PeerResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_AUTH_USER = "AAAAAAAAAA";
    private static final String UPDATED_AUTH_USER = "BBBBBBBBBB";

    private static final String DEFAULT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    private static final Transport DEFAULT_TRANSPORT = Transport.UDP;
    private static final Transport UPDATED_TRANSPORT = Transport.TCP;

    private static final Boolean DEFAULT_REGISTER = false;
    private static final Boolean UPDATED_REGISTER = true;

    private static final Integer DEFAULT_QUALIFY = 1;
    private static final Integer UPDATED_QUALIFY = 2;

    @Autowired
    private PeerRepository peerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeerMockMvc;

    private Peer peer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeerResource peerResource = new PeerResource(peerRepository);
        this.restPeerMockMvc = MockMvcBuilders.standaloneSetup(peerResource)
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
    public static Peer createEntity(EntityManager em) {
        Peer peer = new Peer()
            .description(DEFAULT_DESCRIPTION)
            .user(DEFAULT_USER)
            .authUser(DEFAULT_AUTH_USER)
            .secret(DEFAULT_SECRET)
            .address(DEFAULT_ADDRESS)
            .port(DEFAULT_PORT)
            .transport(DEFAULT_TRANSPORT)
            .register(DEFAULT_REGISTER)
            .qualify(DEFAULT_QUALIFY);
        // Add required entity
        Server server = ServerResourceIntTest.createEntity(em);
        em.persist(server);
        em.flush();
        peer.setServer(server);
        return peer;
    }

    @Before
    public void initTest() {
        peer = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeer() throws Exception {
        int databaseSizeBeforeCreate = peerRepository.findAll().size();

        // Create the Peer
        restPeerMockMvc.perform(post("/api/peers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peer)))
            .andExpect(status().isCreated());

        // Validate the Peer in the database
        List<Peer> peerList = peerRepository.findAll();
        assertThat(peerList).hasSize(databaseSizeBeforeCreate + 1);
        Peer testPeer = peerList.get(peerList.size() - 1);
        assertThat(testPeer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPeer.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testPeer.getAuthUser()).isEqualTo(DEFAULT_AUTH_USER);
        assertThat(testPeer.getSecret()).isEqualTo(DEFAULT_SECRET);
        assertThat(testPeer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPeer.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testPeer.getTransport()).isEqualTo(DEFAULT_TRANSPORT);
        assertThat(testPeer.isRegister()).isEqualTo(DEFAULT_REGISTER);
        assertThat(testPeer.getQualify()).isEqualTo(DEFAULT_QUALIFY);
    }

    @Test
    @Transactional
    public void createPeerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peerRepository.findAll().size();

        // Create the Peer with an existing ID
        peer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeerMockMvc.perform(post("/api/peers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peer)))
            .andExpect(status().isBadRequest());

        // Validate the Peer in the database
        List<Peer> peerList = peerRepository.findAll();
        assertThat(peerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPeers() throws Exception {
        // Initialize the database
        peerRepository.saveAndFlush(peer);

        // Get all the peerList
        restPeerMockMvc.perform(get("/api/peers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(peer.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].authUser").value(hasItem(DEFAULT_AUTH_USER.toString())))
            .andExpect(jsonPath("$.[*].secret").value(hasItem(DEFAULT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].transport").value(hasItem(DEFAULT_TRANSPORT.toString())))
            .andExpect(jsonPath("$.[*].register").value(hasItem(DEFAULT_REGISTER.booleanValue())))
            .andExpect(jsonPath("$.[*].qualify").value(hasItem(DEFAULT_QUALIFY)));
    }
    
    @Test
    @Transactional
    public void getPeer() throws Exception {
        // Initialize the database
        peerRepository.saveAndFlush(peer);

        // Get the peer
        restPeerMockMvc.perform(get("/api/peers/{id}", peer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(peer.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.authUser").value(DEFAULT_AUTH_USER.toString()))
            .andExpect(jsonPath("$.secret").value(DEFAULT_SECRET.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.transport").value(DEFAULT_TRANSPORT.toString()))
            .andExpect(jsonPath("$.register").value(DEFAULT_REGISTER.booleanValue()))
            .andExpect(jsonPath("$.qualify").value(DEFAULT_QUALIFY));
    }

    @Test
    @Transactional
    public void getNonExistingPeer() throws Exception {
        // Get the peer
        restPeerMockMvc.perform(get("/api/peers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeer() throws Exception {
        // Initialize the database
        peerRepository.saveAndFlush(peer);

        int databaseSizeBeforeUpdate = peerRepository.findAll().size();

        // Update the peer
        Peer updatedPeer = peerRepository.findById(peer.getId()).get();
        // Disconnect from session so that the updates on updatedPeer are not directly saved in db
        em.detach(updatedPeer);
        updatedPeer
            .description(UPDATED_DESCRIPTION)
            .user(UPDATED_USER)
            .authUser(UPDATED_AUTH_USER)
            .secret(UPDATED_SECRET)
            .address(UPDATED_ADDRESS)
            .port(UPDATED_PORT)
            .transport(UPDATED_TRANSPORT)
            .register(UPDATED_REGISTER)
            .qualify(UPDATED_QUALIFY);

        restPeerMockMvc.perform(put("/api/peers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeer)))
            .andExpect(status().isOk());

        // Validate the Peer in the database
        List<Peer> peerList = peerRepository.findAll();
        assertThat(peerList).hasSize(databaseSizeBeforeUpdate);
        Peer testPeer = peerList.get(peerList.size() - 1);
        assertThat(testPeer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPeer.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testPeer.getAuthUser()).isEqualTo(UPDATED_AUTH_USER);
        assertThat(testPeer.getSecret()).isEqualTo(UPDATED_SECRET);
        assertThat(testPeer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPeer.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testPeer.getTransport()).isEqualTo(UPDATED_TRANSPORT);
        assertThat(testPeer.isRegister()).isEqualTo(UPDATED_REGISTER);
        assertThat(testPeer.getQualify()).isEqualTo(UPDATED_QUALIFY);
    }

    @Test
    @Transactional
    public void updateNonExistingPeer() throws Exception {
        int databaseSizeBeforeUpdate = peerRepository.findAll().size();

        // Create the Peer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeerMockMvc.perform(put("/api/peers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peer)))
            .andExpect(status().isBadRequest());

        // Validate the Peer in the database
        List<Peer> peerList = peerRepository.findAll();
        assertThat(peerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeer() throws Exception {
        // Initialize the database
        peerRepository.saveAndFlush(peer);

        int databaseSizeBeforeDelete = peerRepository.findAll().size();

        // Get the peer
        restPeerMockMvc.perform(delete("/api/peers/{id}", peer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Peer> peerList = peerRepository.findAll();
        assertThat(peerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Peer.class);
        Peer peer1 = new Peer();
        peer1.setId(1L);
        Peer peer2 = new Peer();
        peer2.setId(peer1.getId());
        assertThat(peer1).isEqualTo(peer2);
        peer2.setId(2L);
        assertThat(peer1).isNotEqualTo(peer2);
        peer1.setId(null);
        assertThat(peer1).isNotEqualTo(peer2);
    }
}
