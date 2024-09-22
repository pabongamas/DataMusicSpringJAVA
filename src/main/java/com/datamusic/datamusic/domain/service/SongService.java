package com.datamusic.datamusic.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.domain.SongUser;
import com.datamusic.datamusic.domain.UserEntity;
import com.datamusic.datamusic.domain.repository.SongRepository;
import com.datamusic.datamusic.persistence.UsuarioRepository;
import com.datamusic.datamusic.web.config.JwtUtil;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

     @Autowired
    private JwtUtil jwtUtil;

       @Autowired
    private UsuarioRepository userRepository;

    public List<Song> getAll(){
        return songRepository.getAll();
    }
    public Page<Song> getAllPageable(int page,int elements,String sortBy,String sortDirection){
        Sort sort=Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageable=PageRequest.of(page, elements, sort);
        return songRepository.getAllPageable(pageable);
    }
    public Page<Song>getSongsLikedByUser(Long idUser,int page,int elements,String sortBy,String sortDirection){
        Sort sort=Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageable=PageRequest.of(page,elements,sort);
        return songRepository.getSongsLikedByUser(idUser, pageable);
    }

    public Optional<Song> getSong(Long songId){
        return songRepository.getSong(songId);
    }
    public List<Song> getSongsByAlbumId(Long albumId){
        return songRepository.getSongsByAlbumId(albumId);
    }
    public List<Song> getSongsByGeneroId(Long genderId){
        return songRepository.getSongsByGeneroId(genderId);
    }
    public List<Song> getSongsByArtistId(Long artistId){
        return songRepository.getSongsByArtistId(artistId);
    }
    public List<Song> getSongsByPlaylist(Long idPlaylist){
        return songRepository.getSongsByPlaylistId(idPlaylist);
    }
    public Page<Song> getSongsByPlaylistPage(Long idPlaylist, int page,int elements,String sortBy,String sortDirection){
        Sort sort=Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageRequest=PageRequest.of(page,elements,sort);
        return songRepository.getSongsByPlaylistIdPage(idPlaylist,pageRequest);
    }
    public Song save(Song song){
        return songRepository.save(song);
    }
    public Optional<Song> getSongByNameAndAlbumId(String name,Long albumId){
        return songRepository.getSongByNameAndAlbumId(name,albumId);
    }
    public boolean delete(Long songId){
        return getSong(songId).map(song->{
            songRepository.delete(songId);
            return true;
        }).orElse(false);
    }

    public List<Song> getSongsLikedOfAlbumByUser(String token,Long albumId){
        //function to get songs liked of album by user 
        //its required the JWT , with this token ,its searched for get the username of the actual user

        String jwt=token.split(" ")[1].trim();
        String username=this.jwtUtil.getUsername(jwt);

        // Once a time  i got the username ,the user is searched through the username
         UserEntity userEntity= this.userRepository.getUserByEmail(username)
        .orElseThrow(()->new UsernameNotFoundException("User "+username+" Not found"));
        Long userId=userEntity.getIdUser();
       return songRepository.getSongsOfAlbumLikedByUser(userId, albumId);
    }

    public boolean songIsLiked(Long songId,Long albumId,String token){
         //function to get songs liked of album by user 
        //its required the JWT , with this token ,its searched for get the username of the actual user

        String jwt=token.split(" ")[1].trim();
        String username=this.jwtUtil.getUsername(jwt);

        // Once a time  i got the username ,the user is searched through the username
         UserEntity userEntity= this.userRepository.getUserByEmail(username)
        .orElseThrow(()->new UsernameNotFoundException("User "+username+" Not found"));
        Long userId=userEntity.getIdUser();
        boolean isLiked=songRepository.songIsLiked(songId, userId, albumId);
        return isLiked;
    }

}
