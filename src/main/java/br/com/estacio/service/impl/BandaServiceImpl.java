package br.com.estacio.service.impl;

import br.com.estacio.domain.Album;
import br.com.estacio.service.BandaService;
import br.com.estacio.domain.Banda;
import br.com.estacio.repository.BandaRepository;
import br.com.estacio.service.dto.AlbumDTO;
import br.com.estacio.service.dto.BandaDTO;
import br.com.estacio.service.mapper.AlbumMapper;
import br.com.estacio.service.mapper.BandaMapper;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Banda.
 */
@Service
@Transactional
public class BandaServiceImpl implements BandaService{

    private final Logger log = LoggerFactory.getLogger(BandaServiceImpl.class);
    
    private final BandaRepository bandaRepository;

    private final BandaMapper bandaMapper;
    
    private final AlbumMapper albumMapper;

    public BandaServiceImpl(BandaRepository bandaRepository, BandaMapper bandaMapper,AlbumMapper albumMapper) {
        this.bandaRepository = bandaRepository;
        this.bandaMapper = bandaMapper;
        this.albumMapper = albumMapper;
    }

    /**
     * Save a banda.
     *
     * @param bandaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BandaDTO save(BandaDTO bandaDTO) {
        log.debug("Request to save Banda : {}", bandaDTO);
        Banda banda = bandaMapper.bandaDTOToBanda(bandaDTO);
        banda = bandaRepository.save(banda);
        BandaDTO result = bandaMapper.bandaToBandaDTO(banda);
        return result;
    }

    /**
     *  Get all the bandas.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BandaDTO> findAll() {
        log.debug("Request to get all Bandas");
        List<BandaDTO> result = bandaRepository.findAll().stream()
            .map(bandaMapper::bandaToBandaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }
    
    @Transactional(readOnly = true)
    public List<AlbumDTO> findAllByBanda(Long id) {
        log.debug("Request to get all Bandas");
        List<Album> result = bandaRepository.findOne(id).getAlbums();
        List<AlbumDTO> list = new ArrayList<>();
        for(Album a : result){
            list.add(albumMapper.albumToAlbumDTO(a));
        };
        return list;
    }

    /**
     *  Get one banda by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BandaDTO findOne(Long id) {
        log.debug("Request to get Banda : {}", id);
        Banda banda = bandaRepository.findOne(id);
        BandaDTO bandaDTO = bandaMapper.bandaToBandaDTO(banda);
        return bandaDTO;
    }

    /**
     *  Delete the  banda by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Banda : {}", id);
        bandaRepository.delete(id);
    }
}
