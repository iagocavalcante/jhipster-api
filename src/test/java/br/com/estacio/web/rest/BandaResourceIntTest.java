package br.com.estacio.web.rest;

import br.com.estacio.TesteJhipApp;

import br.com.estacio.domain.Banda;
import br.com.estacio.repository.BandaRepository;
import br.com.estacio.service.BandaService;
import br.com.estacio.service.dto.BandaDTO;
import br.com.estacio.service.mapper.BandaMapper;
import br.com.estacio.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BandaResource REST controller.
 *
 * @see BandaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteJhipApp.class)
public class BandaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private BandaRepository bandaRepository;

    @Autowired
    private BandaMapper bandaMapper;

    @Autowired
    private BandaService bandaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBandaMockMvc;

    private Banda banda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BandaResource bandaResource = new BandaResource(bandaService);
        this.restBandaMockMvc = MockMvcBuilders.standaloneSetup(bandaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banda createEntity(EntityManager em) {
        Banda banda = new Banda()
            .nome(DEFAULT_NOME);
        return banda;
    }

    @Before
    public void initTest() {
        banda = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanda() throws Exception {
        int databaseSizeBeforeCreate = bandaRepository.findAll().size();

        // Create the Banda
        BandaDTO bandaDTO = bandaMapper.bandaToBandaDTO(banda);
        restBandaMockMvc.perform(post("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bandaDTO)))
            .andExpect(status().isCreated());

        // Validate the Banda in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeCreate + 1);
        Banda testBanda = bandaList.get(bandaList.size() - 1);
        assertThat(testBanda.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createBandaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bandaRepository.findAll().size();

        // Create the Banda with an existing ID
        banda.setId(1L);
        BandaDTO bandaDTO = bandaMapper.bandaToBandaDTO(banda);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBandaMockMvc.perform(post("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bandaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bandaRepository.findAll().size();
        // set the field null
        banda.setNome(null);

        // Create the Banda, which fails.
        BandaDTO bandaDTO = bandaMapper.bandaToBandaDTO(banda);

        restBandaMockMvc.perform(post("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bandaDTO)))
            .andExpect(status().isBadRequest());

        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBandas() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);

        // Get all the bandaList
        restBandaMockMvc.perform(get("/api/bandas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getBanda() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);

        // Get the banda
        restBandaMockMvc.perform(get("/api/bandas/{id}", banda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banda.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBanda() throws Exception {
        // Get the banda
        restBandaMockMvc.perform(get("/api/bandas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanda() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);
        int databaseSizeBeforeUpdate = bandaRepository.findAll().size();

        // Update the banda
        Banda updatedBanda = bandaRepository.findOne(banda.getId());
        updatedBanda
            .nome(UPDATED_NOME);
        BandaDTO bandaDTO = bandaMapper.bandaToBandaDTO(updatedBanda);

        restBandaMockMvc.perform(put("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bandaDTO)))
            .andExpect(status().isOk());

        // Validate the Banda in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeUpdate);
        Banda testBanda = bandaList.get(bandaList.size() - 1);
        assertThat(testBanda.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingBanda() throws Exception {
        int databaseSizeBeforeUpdate = bandaRepository.findAll().size();

        // Create the Banda
        BandaDTO bandaDTO = bandaMapper.bandaToBandaDTO(banda);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBandaMockMvc.perform(put("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bandaDTO)))
            .andExpect(status().isCreated());

        // Validate the Banda in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBanda() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);
        int databaseSizeBeforeDelete = bandaRepository.findAll().size();

        // Get the banda
        restBandaMockMvc.perform(delete("/api/bandas/{id}", banda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banda.class);
    }
}
