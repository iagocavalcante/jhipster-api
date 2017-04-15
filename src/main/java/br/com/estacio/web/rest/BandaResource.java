package br.com.estacio.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.estacio.service.BandaService;
import br.com.estacio.service.dto.AlbumDTO;
import br.com.estacio.web.rest.util.HeaderUtil;
import br.com.estacio.service.dto.BandaDTO;
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
 * REST controller for managing Banda.
 */
@RestController
@RequestMapping("/api")
public class BandaResource {

    private final Logger log = LoggerFactory.getLogger(BandaResource.class);

    private static final String ENTITY_NAME = "banda";
        
    private final BandaService bandaService;

    public BandaResource(BandaService bandaService) {
        this.bandaService = bandaService;
    }

    /**
     * POST  /bandas : Create a new banda.
     *
     * @param bandaDTO the bandaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bandaDTO, or with status 400 (Bad Request) if the banda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bandas")
    @Timed
    public ResponseEntity<BandaDTO> createBanda(@Valid @RequestBody BandaDTO bandaDTO) throws URISyntaxException {
        log.debug("REST request to save Banda : {}", bandaDTO);
        if (bandaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new banda cannot already have an ID")).body(null);
        }
        BandaDTO result = bandaService.save(bandaDTO);
        return ResponseEntity.created(new URI("/api/bandas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bandas : Updates an existing banda.
     *
     * @param bandaDTO the bandaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bandaDTO,
     * or with status 400 (Bad Request) if the bandaDTO is not valid,
     * or with status 500 (Internal Server Error) if the bandaDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bandas")
    @Timed
    public ResponseEntity<BandaDTO> updateBanda(@Valid @RequestBody BandaDTO bandaDTO) throws URISyntaxException {
        log.debug("REST request to update Banda : {}", bandaDTO);
        if (bandaDTO.getId() == null) {
            return createBanda(bandaDTO);
        }
        BandaDTO result = bandaService.save(bandaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bandaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bandas : get all the bandas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bandas in body
     */
    @GetMapping("/bandas")
    @Timed
    public List<BandaDTO> getAllBandas() {
        log.debug("REST request to get all Bandas");
        return bandaService.findAll();
    }

    /**
     * GET  /bandas/:id : get the "id" banda.
     *
     * @param id the id of the bandaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bandaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bandas/{id}")
    @Timed
    public ResponseEntity<BandaDTO> getBanda(@PathVariable Long id) {
        log.debug("REST request to get Banda : {}", id);
        BandaDTO bandaDTO = bandaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bandaDTO));
    }
    
    @GetMapping("/bandas/album/{id}")
    @Timed
    public ResponseEntity<List<AlbumDTO>> getAlbumbyBanda(@PathVariable Long id) {
        log.debug("REST request to get Banda : {}", id);
        List<AlbumDTO> bandaDTO = bandaService.findAllByBanda(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bandaDTO));
    }

    /**
     * DELETE  /bandas/:id : delete the "id" banda.
     *
     * @param id the id of the bandaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bandas/{id}")
    @Timed
    public ResponseEntity<Void> deleteBanda(@PathVariable Long id) {
        log.debug("REST request to delete Banda : {}", id);
        bandaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
