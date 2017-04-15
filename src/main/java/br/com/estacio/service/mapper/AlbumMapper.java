package br.com.estacio.service.mapper;

import br.com.estacio.domain.*;
import br.com.estacio.service.dto.AlbumDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Album and its DTO AlbumDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlbumMapper {

    @Mapping(source = "banda.id", target = "bandaId")
    AlbumDTO albumToAlbumDTO(Album album);

    List<AlbumDTO> albumsToAlbumDTOs(List<Album> albums);

    @Mapping(source = "bandaId", target = "banda")
    @Mapping(target = "musicas", ignore = true)
    Album albumDTOToAlbum(AlbumDTO albumDTO);

    List<Album> albumDTOsToAlbums(List<AlbumDTO> albumDTOs);

    default Banda bandaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Banda banda = new Banda();
        banda.setId(id);
        return banda;
    }
}
