package br.com.estacio.service.impl;

import br.com.estacio.service.MusicaService;
import br.com.estacio.domain.Musica;
import br.com.estacio.repository.MusicaRepository;
import br.com.estacio.service.dto.MusicaDTO;
import br.com.estacio.service.mapper.MusicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Musica.
 */
@Service
@Transactional
public class MusicaServiceImpl implements MusicaService{

    private final Logger log = LoggerFactory.getLogger(MusicaServiceImpl.class);
    
    private final MusicaRepository musicaRepository;

    private final MusicaMapper musicaMapper;

    public MusicaServiceImpl(MusicaRepository musicaRepository, MusicaMapper musicaMapper) {
        this.musicaRepository = musicaRepository;
        this.musicaMapper = musicaMapper;
    }

    /**
     * Save a musica.
     *
     * @param musicaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MusicaDTO save(MusicaDTO musicaDTO) {
        log.debug("Request to save Musica : {}", musicaDTO);
        Musica musica = musicaMapper.musicaDTOToMusica(musicaDTO);
        musica = musicaRepository.save(musica);
        MusicaDTO result = musicaMapper.musicaToMusicaDTO(musica);
        return result;
    }

    /**
     *  Get all the musicas.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MusicaDTO> findAll() {
        log.debug("Request to get all Musicas");
        List<MusicaDTO> result = musicaRepository.findAll().stream()
            .map(musicaMapper::musicaToMusicaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one musica by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MusicaDTO findOne(Long id) {
        log.debug("Request to get Musica : {}", id);
        Musica musica = musicaRepository.findOne(id);
        MusicaDTO musicaDTO = musicaMapper.musicaToMusicaDTO(musica);
        return musicaDTO;
    }

    /**
     *  Delete the  musica by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Musica : {}", id);
        musicaRepository.delete(id);
    }
}
