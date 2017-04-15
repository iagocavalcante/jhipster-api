package br.com.estacio.web.rest;

import br.com.estacio.TesteJhipApp;

import br.com.estacio.domain.Musica;
import br.com.estacio.repository.MusicaRepository;
import br.com.estacio.service.MusicaService;
import br.com.estacio.service.dto.MusicaDTO;
import br.com.estacio.service.mapper.MusicaMapper;
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
 * Test class for the MusicaResource REST controller.
 *
 * @see MusicaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteJhipApp.class)
public class MusicaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private MusicaMapper musicaMapper;

    @Autowired
    private MusicaService musicaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMusicaMockMvc;

    private Musica musica;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MusicaResource musicaResource = new MusicaResource(musicaService);
        this.restMusicaMockMvc = MockMvcBuilders.standaloneSetup(musicaResource)
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
    public static Musica createEntity(EntityManager em) {
        Musica musica = new Musica()
            .nome(DEFAULT_NOME);
        return musica;
    }

    @Before
    public void initTest() {
        musica = createEntity(em);
    }

    @Test
    @Transactional
    public void createMusica() throws Exception {
        int databaseSizeBeforeCreate = musicaRepository.findAll().size();

        // Create the Musica
        MusicaDTO musicaDTO = musicaMapper.musicaToMusicaDTO(musica);
        restMusicaMockMvc.perform(post("/api/musicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musicaDTO)))
            .andExpect(status().isCreated());

        // Validate the Musica in the database
        List<Musica> musicaList = musicaRepository.findAll();
        assertThat(musicaList).hasSize(databaseSizeBeforeCreate + 1);
        Musica testMusica = musicaList.get(musicaList.size() - 1);
        assertThat(testMusica.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createMusicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = musicaRepository.findAll().size();

        // Create the Musica with an existing ID
        musica.setId(1L);
        MusicaDTO musicaDTO = musicaMapper.musicaToMusicaDTO(musica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMusicaMockMvc.perform(post("/api/musicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Musica> musicaList = musicaRepository.findAll();
        assertThat(musicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = musicaRepository.findAll().size();
        // set the field null
        musica.setNome(null);

        // Create the Musica, which fails.
        MusicaDTO musicaDTO = musicaMapper.musicaToMusicaDTO(musica);

        restMusicaMockMvc.perform(post("/api/musicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musicaDTO)))
            .andExpect(status().isBadRequest());

        List<Musica> musicaList = musicaRepository.findAll();
        assertThat(musicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMusicas() throws Exception {
        // Initialize the database
        musicaRepository.saveAndFlush(musica);

        // Get all the musicaList
        restMusicaMockMvc.perform(get("/api/musicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(musica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getMusica() throws Exception {
        // Initialize the database
        musicaRepository.saveAndFlush(musica);

        // Get the musica
        restMusicaMockMvc.perform(get("/api/musicas/{id}", musica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(musica.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMusica() throws Exception {
        // Get the musica
        restMusicaMockMvc.perform(get("/api/musicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMusica() throws Exception {
        // Initialize the database
        musicaRepository.saveAndFlush(musica);
        int databaseSizeBeforeUpdate = musicaRepository.findAll().size();

        // Update the musica
        Musica updatedMusica = musicaRepository.findOne(musica.getId());
        updatedMusica
            .nome(UPDATED_NOME);
        MusicaDTO musicaDTO = musicaMapper.musicaToMusicaDTO(updatedMusica);

        restMusicaMockMvc.perform(put("/api/musicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musicaDTO)))
            .andExpect(status().isOk());

        // Validate the Musica in the database
        List<Musica> musicaList = musicaRepository.findAll();
        assertThat(musicaList).hasSize(databaseSizeBeforeUpdate);
        Musica testMusica = musicaList.get(musicaList.size() - 1);
        assertThat(testMusica.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingMusica() throws Exception {
        int databaseSizeBeforeUpdate = musicaRepository.findAll().size();

        // Create the Musica
        MusicaDTO musicaDTO = musicaMapper.musicaToMusicaDTO(musica);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMusicaMockMvc.perform(put("/api/musicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(musicaDTO)))
            .andExpect(status().isCreated());

        // Validate the Musica in the database
        List<Musica> musicaList = musicaRepository.findAll();
        assertThat(musicaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMusica() throws Exception {
        // Initialize the database
        musicaRepository.saveAndFlush(musica);
        int databaseSizeBeforeDelete = musicaRepository.findAll().size();

        // Get the musica
        restMusicaMockMvc.perform(delete("/api/musicas/{id}", musica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Musica> musicaList = musicaRepository.findAll();
        assertThat(musicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Musica.class);
    }
}
