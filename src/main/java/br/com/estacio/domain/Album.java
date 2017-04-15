package br.com.estacio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A Album.
 */
@Entity
@Table(name = "album")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToOne
    private Banda banda;

    @OneToMany(mappedBy = "album")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Musica> musicas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Album nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Banda getBanda() {
        return banda;
    }

    public Album banda(Banda banda) {
        this.banda = banda;
        return this;
    }

    public void setBanda(Banda banda) {
        this.banda = banda;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public Album musicas(List<Musica> musicas) {
        this.musicas = musicas;
        return this;
    }

    public Album addMusica(Musica musica) {
        this.musicas.add(musica);
        musica.setAlbum(this);
        return this;
    }

    public Album removeMusica(Musica musica) {
        this.musicas.remove(musica);
        musica.setAlbum(null);
        return this;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Album album = (Album) o;
        if (album.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Album{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            '}';
    }
}
