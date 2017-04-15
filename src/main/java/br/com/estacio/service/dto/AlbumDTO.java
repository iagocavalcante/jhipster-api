package br.com.estacio.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Album entity.
 */
public class AlbumDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private Long bandaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getBandaId() {
        return bandaId;
    }

    public void setBandaId(Long bandaId) {
        this.bandaId = bandaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlbumDTO albumDTO = (AlbumDTO) o;

        if ( ! Objects.equals(id, albumDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlbumDTO{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            '}';
    }
}
