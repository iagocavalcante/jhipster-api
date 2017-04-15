package br.com.estacio.service.impl;

import br.com.estacio.service.AlbumService;
import br.com.estacio.domain.Album;
import br.com.estacio.domain.Musica;
import br.com.estacio.repository.AlbumRepository;
import br.com.estacio.service.dto.AlbumDTO;
import br.com.estacio.service.dto.MusicaDTO;
import br.com.estacio.service.mapper.AlbumMapper;
import br.com.estacio.service.mapper.MusicaMapper;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Album.
 */
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService{

    private final Logger log = LoggerFactory.getLogger(AlbumServiceImpl.class);
    
    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;
    
    private final MusicaMapper musicaMapper;

    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumMapper albumMapper, MusicaMapper musicaMapper) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.musicaMapper = musicaMapper;
    }

    /**
     * Save a album.
     *
     * @param albumDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AlbumDTO save(AlbumDTO albumDTO) {
        log.debug("Request to save Album : {}", albumDTO);
        Album album = albumMapper.albumDTOToAlbum(albumDTO);
        album = albumRepository.save(album);
        AlbumDTO result = albumMapper.albumToAlbumDTO(album);
        return result;
    }

    /**
     *  Get all the albums.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AlbumDTO> findAll() {
        log.debug("Request to get all Albums");
        List<AlbumDTO> result = albumRepository.findAll().stream()
            .map(albumMapper::albumToAlbumDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one album by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AlbumDTO findOne(Long id) {
        log.debug("Request to get Album : {}", id);
        Album album = albumRepository.findOne(id);
        AlbumDTO albumDTO = albumMapper.albumToAlbumDTO(album);
        return albumDTO;
    }

    /**
     *  Delete the  album by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Album : {}", id);
        albumRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<MusicaDTO> findAllByAlbum(Long id) {
        log.debug("Request to get all Bandas");
        List<Musica> result = albumRepository.findOne(id).getMusicas();
        List<MusicaDTO> list = new ArrayList<>();
        for(Musica a : result){
            list.add(musicaMapper.musicaToMusicaDTO(a));
        };
        return list;
    }
}
