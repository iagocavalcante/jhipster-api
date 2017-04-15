package br.com.estacio.service.mapper;

import br.com.estacio.domain.*;
import br.com.estacio.service.dto.BandaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Banda and its DTO BandaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BandaMapper {

    BandaDTO bandaToBandaDTO(Banda banda);

    List<BandaDTO> bandasToBandaDTOs(List<Banda> bandas);

    @Mapping(target = "albums", ignore = true)
    Banda bandaDTOToBanda(BandaDTO bandaDTO);

    List<Banda> bandaDTOsToBandas(List<BandaDTO> bandaDTOs);
}
