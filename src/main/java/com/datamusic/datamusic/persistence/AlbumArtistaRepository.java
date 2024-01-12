package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.domain.repository.AlbumArtistRepository;
import com.datamusic.datamusic.persistence.crud.AlbumArtistaCrudRepository;
import com.datamusic.datamusic.persistence.entity.AlbumsArtista;
import com.datamusic.datamusic.persistence.mapper.AlbumArtistMapMapper;
import com.datamusic.datamusic.persistence.mapper.AlbumArtistMapper;

@Repository
public class AlbumArtistaRepository implements AlbumArtistRepository {

    @Autowired
    private AlbumArtistaCrudRepository albumArtistaCrudRepository;

    @Autowired
    private AlbumArtistMapper mapper;

    @Autowired
    private AlbumArtistMapMapper mapperMap;

    @Override
    public List<AlbumArtist> getAll() {
        List<AlbumsArtista> albumsArtista = (List<AlbumsArtista>) albumArtistaCrudRepository.findAll();
        return mapperMap.toAlbumArtistMap(albumsArtista);
    }

    @Override
    public List<AlbumArtist> getAlbumArtistByAlbumId(Long albumId) {
        List<AlbumsArtista> albumsArtistaByAlbumId = (List<AlbumsArtista>) albumArtistaCrudRepository
                .findByIdIdAlbum(albumId);
        // for (AlbumsArtista aa : albumsArtistaByAlbumId) {
        //     aa.getArtista().getAlbums().forEach(album -> {
        //     });
        // }
        return mapperMap.toAlbumArtistMap(albumsArtistaByAlbumId);
    }

    @Override
    public List<AlbumArtist> getAlbumArtistByArtistId(Long idArtist) {
        List<AlbumsArtista> albumsArtistaByArtistId = (List<AlbumsArtista>) albumArtistaCrudRepository
                .findByIdIdArtista(idArtist);
        
        List<AlbumArtist> list = new ArrayList<>();       
        for(AlbumsArtista obj:albumsArtistaByArtistId){
            AlbumArtist objeto=mapperMap.toAlbumArtistMap(obj);
            list.add(objeto);
        }
        return list;
    }

}
