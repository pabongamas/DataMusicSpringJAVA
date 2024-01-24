package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.domain.repository.AlbumArtistRepository;
import com.datamusic.datamusic.persistence.crud.AlbumArtistaCrudRepository;
import com.datamusic.datamusic.persistence.crud.AlbumCrudRepository;
import com.datamusic.datamusic.persistence.crud.ArtistaCrudRepository;
import com.datamusic.datamusic.persistence.entity.AlbumEntity;
import com.datamusic.datamusic.persistence.entity.AlbumsArtista;
import com.datamusic.datamusic.persistence.entity.AlbumsArtistaPK;
import com.datamusic.datamusic.persistence.entity.Artista;
import com.datamusic.datamusic.persistence.mapper.AlbumArtistMapMapper;

import jakarta.transaction.Transactional;

@Repository
public class AlbumArtistaRepository implements AlbumArtistRepository {

    @Autowired
    private AlbumArtistaCrudRepository albumArtistaCrudRepository;

    @Autowired
    private AlbumCrudRepository albumCrudRepository;

    @Autowired
    private ArtistaCrudRepository artistaCrudRepository;

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
        // aa.getArtista().getAlbums().forEach(album -> {
        // });
        // }
        return mapperMap.toAlbumArtistMap(albumsArtistaByAlbumId);
    }

    @Override
    public List<AlbumArtist> getAlbumArtistByArtistId(Long idArtist) {
        List<AlbumsArtista> albumsArtistaByArtistId = (List<AlbumsArtista>) albumArtistaCrudRepository
                .findByIdIdArtista(idArtist);

        List<AlbumArtist> list = new ArrayList<>();
        for (AlbumsArtista obj : albumsArtistaByArtistId) {
            AlbumArtist objeto = mapperMap.toAlbumArtistMap(obj);
            list.add(objeto);
        }
        return list;
    }

    @Override
    public AlbumArtist save(AlbumArtist albumArtist) {
        AlbumsArtista albumArtista = mapperMap.toAlbumsArtistaMap(albumArtist);

        if (albumArtista.getAlbum() == null) {
            // set album se debe enviar el album y el artista para que esten todos los
            // campos de la entidad AlbumsArtista
            Optional<AlbumEntity> album = albumCrudRepository.findById(albumArtist.getAlbumId());
            album.ifPresent(albumEntity -> albumArtista.setAlbum(albumEntity));

            Optional<Artista> artista = artistaCrudRepository.findById(albumArtist.getArtistId());
            artista.ifPresent(artistaEntity -> albumArtista.setArtista(artistaEntity));
        }

        AlbumsArtista saved = albumArtistaCrudRepository.save(albumArtista);
        return mapperMap.toAlbumArtistMap(saved);
    }

    @Override
    public void delete(Long idAlbum, Long idArtista) {
        try {
            AlbumsArtistaPK id = new AlbumsArtistaPK(idAlbum, idArtista);
            albumArtistaCrudRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Override
    public List<AlbumArtist> getAlbumArtistByAlbumIdAndArtistId(Long idAlbum, Long idArtist) {
        List<AlbumsArtista> albumsArtista = (List<AlbumsArtista>) albumArtistaCrudRepository.findByIdIdAlbumAndIdIdArtista(idAlbum, idArtist);
        return mapperMap.toAlbumArtistMap(albumsArtista);
    }

}
