package br.com.estacio.repository;

import br.com.estacio.domain.Banda;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Banda entity.
 */
@SuppressWarnings("unused")
public interface BandaRepository extends JpaRepository<Banda,Long> {

}
