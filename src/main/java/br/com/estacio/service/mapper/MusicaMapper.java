package br.com.estacio.service.mapper;

import br.com.estacio.domain.*;
import br.com.estacio.service.dto.MusicaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Musica and its DTO MusicaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MusicaMapper {

    @Mapping(source = "album.id", target = "albumId", ignore = true)
    MusicaDTO musicaToMusicaDTO(Musica musica);

    List<MusicaDTO> musicasToMusicaDTOs(List<Musica> musicas);

    @Mapping(source = "albumId", target = "album")
    Musica musicaDTOToMusica(MusicaDTO musicaDTO);

    List<Musica> musicaDTOsToMusicas(List<MusicaDTO> musicaDTOs);

    default Album albumFromId(Long id) {
        if (id == null) {
            return null;
        }
        Album album = new Album();
        album.setId(id);
        return album;
    }
}
