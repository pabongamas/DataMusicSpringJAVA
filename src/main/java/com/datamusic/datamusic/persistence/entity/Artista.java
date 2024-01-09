package com.datamusic.datamusic.persistence.entity;

import java.util.List;

// import com.datamusic.datamusic.domain.Album;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artista")
    private Long idArtista;


    private String nombre;

    @OneToMany(mappedBy = "artista",cascade = {CascadeType.ALL})
    private List<AlbumsArtista> albums;

  //  @OneToMany(mappedBy = "idAlbum",cascade = {CascadeType.ALL})
  //   private List<AlbumEntity>albumsInformacion;


    public Long getIdArtista() {
      return idArtista;
    }


    public void setIdArtista(Long idArtista) {
      this.idArtista = idArtista;
    }


    public String getNombre() {
      return nombre;
    }


    public void setNombre(String nombre) {
      this.nombre = nombre;
    }


    public List<AlbumsArtista> getAlbums() {
      return albums;
    }


    public void setAlbums(List<AlbumsArtista> albums) {
      this.albums = albums;
    }


    // public List<AlbumEntity> getAlbumsInformacion() {
    //   return albumsInformacion;
    // }


    // public void setAlbumsInformacion(List<AlbumEntity> albumsInformacion) {
    //   this.albumsInformacion = albumsInformacion;
    // }
    

    




    
}
