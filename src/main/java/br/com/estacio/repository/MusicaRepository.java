package br.com.estacio.repository;

import br.com.estacio.domain.Musica;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Musica entity.
 */
@SuppressWarnings("unused")
public interface MusicaRepository extends JpaRepository<Musica,Long> {

}
