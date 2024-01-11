package com.datamusic.datamusic.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.domain.repository.AlbumArtistRepository;
import com.datamusic.datamusic.persistence.crud.AlbumArtistaCrudRepository;
import com.datamusic.datamusic.persistence.entity.AlbumsArtista;
import com.datamusic.datamusic.persistence.mapper.AlbumArtistMapper;

@Repository
public class AlbumArtistaRepository implements AlbumArtistRepository {

    @Autowired
    private AlbumArtistaCrudRepository albumArtistaCrudRepository;

    @Autowired
    private AlbumArtistMapper mapper;

    @Override
    public List<AlbumArtist> getAll() {
        List<AlbumsArtista> albumsArtista = (List<AlbumsArtista>) albumArtistaCrudRepository.findAll();
        return mapper.toAlbumArtist(albumsArtista);
    }

    @Override
    public List<AlbumArtist> getAlbumArtistByAlbumId(Long albumId) {
        List<AlbumsArtista> albumsArtistaByAlbumId = (List<AlbumsArtista>) albumArtistaCrudRepository
                .findByIdIdAlbum(albumId);
        for (AlbumsArtista aa : albumsArtistaByAlbumId) {
            System.out.println("Album : " + aa.getAlbum().getNombre());
            aa.getArtista().getAlbums().forEach(album -> {
                System.out.println(album.getArtista().getNombre());
            });
        }
        return mapper.toAlbumArtist(albumsArtistaByAlbumId);
    }

    @Override
    public List<AlbumArtist> getAlbumArtistByArtistId(Long idArtist) {
        List<AlbumsArtista> albumsArtistaByArtistId = (List<AlbumsArtista>) albumArtistaCrudRepository
                .findByIdIdArtista(idArtist);
        return mapper.toAlbumArtist(albumsArtistaByArtistId);
    }

}
