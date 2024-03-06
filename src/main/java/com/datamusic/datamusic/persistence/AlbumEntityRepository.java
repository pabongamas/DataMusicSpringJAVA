package com.datamusic.datamusic.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.repository.AlbumRepository;
import com.datamusic.datamusic.persistence.crud.AlbumCrudRepository;
import com.datamusic.datamusic.persistence.entity.AlbumEntity;
import com.datamusic.datamusic.persistence.mapper.AlbumMapper;
import com.datamusic.datamusic.persistence.pageableAndSort.AlbumPagSortRepository;

@Repository
public class AlbumEntityRepository implements AlbumRepository {

    @Autowired
    private AlbumCrudRepository albumCrudRepository;

    @Autowired
    private AlbumMapper mapper;

    @Autowired
    private AlbumPagSortRepository albumPagSortRepository;

    @Override
    public List<Album> getAll() {
        List<AlbumEntity> albums = (List<AlbumEntity>) albumCrudRepository.findAll();
        return mapper.toAlbum(albums);
    }

    @Override
    public Optional<Album> getAlbumById(Long albumId) {
        return albumCrudRepository.findById(albumId).map(album -> mapper.toAlbum(album));
    }

    @Override
    public Album save(Album album) {
        AlbumEntity albumEntity = mapper.toAlbumEntity(album);
        return mapper.toAlbum(albumCrudRepository.save(albumEntity));
    }

    @Override
    public void delete(Long albumId) {
        try {
            albumCrudRepository.deleteById(albumId);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Override
    public List<Album> getAlbumsByGender(Long genderId) {
        List<AlbumEntity> albums = (List<AlbumEntity>) albumCrudRepository.findByIdGenero(genderId);
        return mapper.toAlbum(albums);

    }

    @Override
    public Page<Album> getAllByPage(Pageable pageable) {
        //recibo el obejeto de pageable y utilizo el pagsortrepository ,este me retorna un obejeto de page album entity
        Page<AlbumEntity> listAlbumsByPage = albumPagSortRepository.findAll(pageable);
        //obtengo el contenido de  los albums y lo seteo a una lista de album entity , es posible porque el getcontent es una lista del tipo
        // que se este obteniendo del objeto page
        List<AlbumEntity> contentAlbums = listAlbumsByPage.getContent();
        //como necesito retornar un page de album primero debo mapear los elementos de list albumentity a list album
        List<Album> albumMapPageable = mapper.toAlbum(contentAlbums);
        // una vez hecho esto como necesito retornar un page album en vez de un list album  
        // se utiliza el constructor PageImpl para crear una nueva instancia de Page<Album>.
        //  Se pasa la lista de álbumes mapeados, el objeto Pageable original y la cantidad total de elementos para construir la página.
        //  Esto permitirá devolver una página paginada de objetos Album.
        return new PageImpl<>(albumMapPageable, pageable, listAlbumsByPage.getTotalElements());
    }

    @Override
    public Page<Album> getAlbumsByGenderByPage(Long genderId, Pageable pageable) {
        Page<AlbumEntity> listAlbumsByPage = albumPagSortRepository.findByIdGenero(genderId,pageable);
        List<AlbumEntity> contentAlbums = listAlbumsByPage.getContent();
        List<Album> albumMapPageable = mapper.toAlbum(contentAlbums);
        return new PageImpl<>(albumMapPageable, pageable, listAlbumsByPage.getTotalElements());

     }
}
