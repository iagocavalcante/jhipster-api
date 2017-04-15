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
 * A Banda.
 */
@Entity
@Table(name = "banda")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Banda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "banda")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Album> albums = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Banda nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public Banda albums(List<Album> albums) {
        this.albums = albums;
        return this;
    }

    public Banda addAlbum(Album album) {
        this.albums.add(album);
        album.setBanda(this);
        return this;
    }

    public Banda removeAlbum(Album album) {
        this.albums.remove(album);
        album.setBanda(null);
        return this;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Banda banda = (Banda) o;
        if (banda.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, banda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Banda{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            '}';
    }
}
