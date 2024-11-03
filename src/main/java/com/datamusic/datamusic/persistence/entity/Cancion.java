package com.datamusic.datamusic.persistence.entity;


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


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "canciones")
public class Cancion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancion")
    private Long idCancion;

    private String nombre;

    private Long duracion;

    @Column(name = "id_album")
    private Long idAlbum;

    @Column(name="numero_cancion")
    private Long numeroCancion;

    private Boolean  explicita;


    @ManyToOne
    @JoinColumn(name = "id_album", insertable = false, updatable = false)
    private AlbumEntity album;

    @OneToMany(mappedBy = "cancion",cascade = {CascadeType.ALL})
    private List<CancionPlaylist> playlists;

    @OneToMany(mappedBy = "cancion",cascade = {CascadeType.ALL})
    private List<CancionesUsuarios> usuarios;


    @Column(name="nombre_archivo")
    private String nombre_archivo;



    public Long getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(Long idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getDuracion() {
        return duracion;
    }

    public void setDuracion(Long duracion) {
        this.duracion = duracion;
    }

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public AlbumEntity getAlbum() {
        return album;
    }

    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }

    public List<CancionPlaylist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<CancionPlaylist> playlists) {
        this.playlists = playlists;
    }

    public Long getNumero_cancion() {
        return numeroCancion;
    }

    public void setNumero_cancion(Long numero_cancion) {
        this.numeroCancion = numero_cancion;
    }

    public Boolean  isExplicita() {
        return explicita;
    }

    public void setExplicita(Boolean  explicita) {
        this.explicita = explicita;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }
    

    
   

    
}
