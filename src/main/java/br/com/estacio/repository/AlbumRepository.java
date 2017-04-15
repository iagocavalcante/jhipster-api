package br.com.estacio.repository;

import br.com.estacio.domain.Album;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Album entity.
 */
@SuppressWarnings("unused")
public interface AlbumRepository extends JpaRepository<Album,Long> {

}
