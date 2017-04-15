package br.com.estacio.service;

import br.com.estacio.service.dto.AlbumDTO;
import br.com.estacio.service.dto.BandaDTO;
import java.util.List;

/**
 * Service Interface for managing Banda.
 */
public interface BandaService {

    public List<AlbumDTO> findAllByBanda(Long id);
    /**
     * Save a banda.
     *
     * @param bandaDTO the entity to save
     * @return the persisted entity
     */
    BandaDTO save(BandaDTO bandaDTO);

    /**
     *  Get all the bandas.
     *  
     *  @return the list of entities
     */
    List<BandaDTO> findAll();

    /**
     *  Get the "id" banda.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BandaDTO findOne(Long id);

    /**
     *  Delete the "id" banda.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
