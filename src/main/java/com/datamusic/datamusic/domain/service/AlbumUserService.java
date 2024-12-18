package com.datamusic.datamusic.domain.service;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.AlbumUser;
import com.datamusic.datamusic.domain.SongUser;
import com.datamusic.datamusic.domain.UserEntity;
import com.datamusic.datamusic.domain.repository.AlbumUserRepository;
import com.datamusic.datamusic.persistence.UsuarioRepository;
import com.datamusic.datamusic.web.config.JwtUtil;

@Service
public class AlbumUserService {
    @Autowired
    private AlbumUserRepository albumUserRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository userRepository;

    public List<AlbumUser> getAll() {
        List<AlbumUser> albumUserAll = albumUserRepository.getAll();
        Iterator<AlbumUser> iteratorAlbumUser = albumUserAll.iterator();
        while (iteratorAlbumUser.hasNext()) {
            AlbumUser AlbumUser = iteratorAlbumUser.next();
            AlbumUser.setUser(null);
            AlbumUser.setAlbum(null);
            if (AlbumUser.getDateAdd() != null) {
                AlbumUser.setDateAddText(
                        AlbumUser.getDateAdd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            AlbumUser.setDateAdd(null);
        }
        return albumUserAll;
    }

    public Page<AlbumUser> getAllByPage(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return albumUserRepository.getAllByPage(pageRequest);
    }

    public List<AlbumUser> getAlbumsLikedByUser(String token) {
        String jwt = token.split(" ")[1].trim();
        String username = this.jwtUtil.getUsername(jwt);

        // Once a time i got the username ,the user is searched through the username
        UserEntity userEntity = this.userRepository.getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " Not found"));
        Long userId = userEntity.getIdUser();
        List<AlbumUser> albumsUserLiked=albumUserRepository.likedAlbumsByUser(userId);
        return albumsUserLiked;
    }
    public AlbumUser likeAlbumUser(Long idAlbum,String token){
        //function to get songs liked of album by user 
        //its required the JWT , with this token ,its searched for get the username of the actual user

        String jwt=token.split(" ")[1].trim();
        String username=this.jwtUtil.getUsername(jwt);

        // Once a time  i got the username ,the user is searched through the username
         UserEntity userEntity= this.userRepository.getUserByEmail(username)
        .orElseThrow(()->new UsernameNotFoundException("User "+username+" Not found"));
        Long userId=userEntity.getIdUser();
        AlbumUser  likedAlbum= albumUserRepository.likedAlbum(userId, idAlbum);
        likedAlbum.setUser(null);
        likedAlbum.setIdUser(null);
        likedAlbum.setAlbum(null);
        return likedAlbum;
      }

      public boolean dislikeAlbumUser(Long idAlbum,String token){
        String jwt=token.split(" ")[1].trim();
        String username=this.jwtUtil.getUsername(jwt);
          // Once a time  i got the username ,the user is searched through the username
          UserEntity userEntity= this.userRepository.getUserByEmail(username)
          .orElseThrow(()->new UsernameNotFoundException("User "+username+" Not found"));
          Long userId=userEntity.getIdUser();
          boolean dislikedAlbum=albumUserRepository.dislikeAlbum(userId, idAlbum);
          return dislikedAlbum;
      }
}
