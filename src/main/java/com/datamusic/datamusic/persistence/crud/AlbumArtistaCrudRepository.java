package com.datamusic.datamusic.persistence.crud;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.datamusic.datamusic.persistence.entity.AlbumsArtista;
import com.datamusic.datamusic.persistence.entity.AlbumsArtistaPK;

public interface AlbumArtistaCrudRepository extends CrudRepository<AlbumsArtista,AlbumsArtistaPK> {
    
     List<AlbumsArtista> findByIdIdAlbum(Long idAlbum);

     List<AlbumsArtista> findByIdIdArtista(Long idArtist);

     List<AlbumsArtista> findByIdIdAlbumAndIdIdArtista(Long idAlbum,Long idArtist);

     void deleteByIdIdAlbumAndIdIdArtista(Long idAlbum,Long idArtist);
}
