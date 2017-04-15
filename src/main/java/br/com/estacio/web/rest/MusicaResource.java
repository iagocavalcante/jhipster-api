package br.com.estacio.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.estacio.service.MusicaService;
import br.com.estacio.web.rest.util.HeaderUtil;
import br.com.estacio.service.dto.MusicaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Musica.
 */
@RestController
@RequestMapping("/api")
public class MusicaResource {

    private final Logger log = LoggerFactory.getLogger(MusicaResource.class);

    private static final String ENTITY_NAME = "musica";
        
    private final MusicaService musicaService;

    public MusicaResource(MusicaService musicaService) {
        this.musicaService = musicaService;
    }

    /**
     * POST  /musicas : Create a new musica.
     *
     * @param musicaDTO the musicaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new musicaDTO, or with status 400 (Bad Request) if the musica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/musicas")
    @Timed
    public ResponseEntity<MusicaDTO> createMusica(@Valid @RequestBody MusicaDTO musicaDTO) throws URISyntaxException {
        log.debug("REST request to save Musica : {}", musicaDTO);
        if (musicaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new musica cannot already have an ID")).body(null);
        }
        MusicaDTO result = musicaService.save(musicaDTO);
        return ResponseEntity.created(new URI("/api/musicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /musicas : Updates an existing musica.
     *
     * @param musicaDTO the musicaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated musicaDTO,
     * or with status 400 (Bad Request) if the musicaDTO is not valid,
     * or with status 500 (Internal Server Error) if the musicaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/musicas")
    @Timed
    public ResponseEntity<MusicaDTO> updateMusica(@Valid @RequestBody MusicaDTO musicaDTO) throws URISyntaxException {
        log.debug("REST request to update Musica : {}", musicaDTO);
        if (musicaDTO.getId() == null) {
            return createMusica(musicaDTO);
        }
        MusicaDTO result = musicaService.save(musicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, musicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /musicas : get all the musicas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of musicas in body
     */
    @GetMapping("/musicas")
    @Timed
    public List<MusicaDTO> getAllMusicas() {
        log.debug("REST request to get all Musicas");
        return musicaService.findAll();
    }

    /**
     * GET  /musicas/:id : get the "id" musica.
     *
     * @param id the id of the musicaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the musicaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/musicas/{id}")
    @Timed
    public ResponseEntity<MusicaDTO> getMusica(@PathVariable Long id) {
        log.debug("REST request to get Musica : {}", id);
        MusicaDTO musicaDTO = musicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(musicaDTO));
    }

    /**
     * DELETE  /musicas/:id : delete the "id" musica.
     *
     * @param id the id of the musicaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/musicas/{id}")
    @Timed
    public ResponseEntity<Void> deleteMusica(@PathVariable Long id) {
        log.debug("REST request to delete Musica : {}", id);
        musicaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
