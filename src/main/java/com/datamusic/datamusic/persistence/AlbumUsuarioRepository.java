package com.datamusic.datamusic.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.datamusic.datamusic.domain.AlbumUser;
import com.datamusic.datamusic.domain.repository.AlbumUserRepository;
import com.datamusic.datamusic.persistence.crud.AlbumCrudRepository;
import com.datamusic.datamusic.persistence.crud.AlbumUsuarioCrudRepository;
import com.datamusic.datamusic.persistence.crud.UsuarioCrudRepository;
import com.datamusic.datamusic.persistence.entity.AlbumEntity;
import com.datamusic.datamusic.persistence.entity.AlbumsUsuario;
import com.datamusic.datamusic.persistence.entity.AlbumsUsuarioPK;
import com.datamusic.datamusic.persistence.entity.Cancion;
import com.datamusic.datamusic.persistence.entity.CancionesUsuarios;
import com.datamusic.datamusic.persistence.entity.CancionesUsuariosPK;
import com.datamusic.datamusic.persistence.entity.Usuario;
import com.datamusic.datamusic.persistence.mapper.AlbumUserMapper;
import com.datamusic.datamusic.persistence.pageableAndSort.AlbumUsuarioPagSortRepository;

@Repository
public class AlbumUsuarioRepository implements AlbumUserRepository {

      @Autowired
    private AlbumUsuarioPagSortRepository albumUsuarioPagSortRepository;

    @Autowired
    private AlbumUsuarioCrudRepository albumUsuarioCrudRepository;

    @Autowired
    private UsuarioCrudRepository UsuarioCrudRepository;

    @Autowired
    private AlbumUserMapper albumUserMapper;

    @Autowired
    private AlbumCrudRepository albumCrudRepository;

    

    @Override
    public List<AlbumUser> getAll() {
       List<AlbumsUsuario> AlbumsUsuario = (List<AlbumsUsuario>) albumUsuarioCrudRepository.findAll();
       return albumUserMapper.toAlbumAUser(AlbumsUsuario);
    }

    @Override
    public Page<AlbumUser> getAllByPage(Pageable pageable) {
        Page<AlbumsUsuario> albumUsuarioPage=albumUsuarioPagSortRepository.findAll(pageable);
        albumUsuarioPage.forEach(t ->System.out.println(t.getAlbum().getNombre()));
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllByPage'");
    }

    @Override
    public List<AlbumUser> likedAlbumsByUser(Long userId) {
        List<AlbumsUsuario> albumsByUserLiked=albumUsuarioCrudRepository.findByIdIdUsuario(userId);
        albumsByUserLiked.forEach(album->{
            album.setUsuario(null);
            // album.setAlbum(null);
            album.getId().setIdUsuario(null);
        });
        return albumUserMapper.toAlbumAUser(albumsByUserLiked);
    }

    @Override
    public AlbumUser likedAlbum(Long userId, Long albumId) {
        Optional<Usuario>usuario=UsuarioCrudRepository.findById(userId);
        if(!usuario.isEmpty()){
            //create object primary key 
            AlbumsUsuarioPK CancionesUAlbumsUsuariosPK=new AlbumsUsuarioPK();
            CancionesUAlbumsUsuariosPK.setIdAlbum(albumId);
            CancionesUAlbumsUsuariosPK.setIdUsuario(userId);

            //create object cancion 
            Optional<AlbumEntity> album=albumCrudRepository.findById(albumId);

            if(album.isEmpty()){
               throw new ResponseStatusException(
                    HttpStatus.CONFLICT,"album Not Found");
            }

            //create object AlbumsUsuario
            AlbumsUsuario albumsUsuario=new AlbumsUsuario();
            albumsUsuario.setId(CancionesUAlbumsUsuariosPK);
            albumsUsuario.setUsuario(usuario.get());
            albumsUsuario.setAlbum(album.get());
            albumsUsuario.setFecha_agregada(LocalDateTime.now());

            //save liked song and mapper for return
            AlbumsUsuario  AlbumsUsuarioSaved = albumUsuarioCrudRepository.save(albumsUsuario);
            return albumUserMapper.toAlbumAUser(AlbumsUsuarioSaved);
        }
        return null;
    }

    @Override
    public boolean dislikeAlbum(Long userId, Long albumId) {
        Optional<Usuario>usuario=UsuarioCrudRepository.findById(userId);
        if(!usuario.isEmpty()){
            //create object primary key 
            AlbumsUsuarioPK AlbumsUsuarioPK=new AlbumsUsuarioPK();
            AlbumsUsuarioPK.setIdAlbum(albumId);
            AlbumsUsuarioPK.setIdUsuario(userId);

            //create object album 
            Optional<AlbumEntity> album=albumCrudRepository.findById(albumId);

            if(album.isEmpty()){
               throw new ResponseStatusException(
                    HttpStatus.CONFLICT,"album Not Found");
            }

            AlbumsUsuario  AlbumsUsuario =albumUsuarioCrudRepository.findByIdIdUsuarioAndIdIdAlbum(userId, albumId);

            //remove liked album of table
            albumUsuarioCrudRepository.delete(AlbumsUsuario);
            return true;
        }
        return false;
    }
    
}
