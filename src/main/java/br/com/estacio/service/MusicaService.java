package br.com.estacio.service;

import br.com.estacio.service.dto.MusicaDTO;
import java.util.List;

/**
 * Service Interface for managing Musica.
 */
public interface MusicaService {

    /**
     * Save a musica.
     *
     * @param musicaDTO the entity to save
     * @return the persisted entity
     */
    MusicaDTO save(MusicaDTO musicaDTO);

    /**
     *  Get all the musicas.
     *  
     *  @return the list of entities
     */
    List<MusicaDTO> findAll();

    /**
     *  Get the "id" musica.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MusicaDTO findOne(Long id);

    /**
     *  Delete the "id" musica.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
