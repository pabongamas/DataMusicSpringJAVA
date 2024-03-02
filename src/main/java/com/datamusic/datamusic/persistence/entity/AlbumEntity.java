package com.datamusic.datamusic.persistence.entity;

import java.util.List;

// import com.datamusic.datamusic.domain.Artist;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "albums")
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_album")
    private Long idAlbum;

    private String nombre;

    private Integer anio;

    @Column(name = "id_genero")
    private Long idGenero;

    @ManyToOne
    @JoinColumn(name = "id_genero", insertable = false, updatable = false)
    private Genero genero;

    @OneToMany(mappedBy = "album",cascade = {CascadeType.ALL})
    private List<AlbumsArtista> artistas;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Long idGenero) {
        this.idGenero = idGenero;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public List<AlbumsArtista> getArtistas() {
        return artistas;
    }

    public void setArtistas(List<AlbumsArtista> artistas) {
        this.artistas = artistas;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    
    

}
