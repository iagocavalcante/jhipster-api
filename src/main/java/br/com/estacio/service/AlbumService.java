package br.com.estacio.service;

import br.com.estacio.service.dto.AlbumDTO;
import br.com.estacio.service.dto.MusicaDTO;
import java.util.List;

/**
 * Service Interface for managing Album.
 */
public interface AlbumService {

    public List<MusicaDTO> findAllByAlbum (Long id);
    /**
     * Save a album.
     *
     * @param albumDTO the entity to save
     * @return the persisted entity
     */
    AlbumDTO save(AlbumDTO albumDTO);

    /**
     *  Get all the albums.
     *  
     *  @return the list of entities
     */
    List<AlbumDTO> findAll();

    /**
     *  Get the "id" album.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AlbumDTO findOne(Long id);

    /**
     *  Delete the "id" album.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
