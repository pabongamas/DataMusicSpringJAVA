package com.datamusic.datamusic.domain.service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.SongUser;
import com.datamusic.datamusic.domain.UserEntity;
import com.datamusic.datamusic.domain.repository.SongUserRepository;
import com.datamusic.datamusic.persistence.UsuarioRepository;
import com.datamusic.datamusic.web.config.JwtUtil;

@Service
public class SongUserService {

    @Autowired
    private SongUserRepository songUserRepository;

      @Autowired
    private JwtUtil jwtUtil;

       @Autowired
    private UsuarioRepository userRepository;

    public List<SongUser>getAll(){
        List<SongUser> songUsersAll= songUserRepository.getAll();
        Iterator<SongUser> iteratorSongUser=songUsersAll.iterator();
        while(iteratorSongUser.hasNext()){
            SongUser songUser=iteratorSongUser.next();
            songUser.setUser(null);
            songUser.setSong(null);
            songUser.setDateAddText(songUser.getDateAdd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            songUser.setDateAdd(null);
        }
        return songUsersAll;
    }
    public SongUser likeSong(Long idSong,String token){
        //function to get songs liked of album by user 
        //its required the JWT , with this token ,its searched for get the username of the actual user

        String jwt=token.split(" ")[1].trim();
        String username=this.jwtUtil.getUsername(jwt);

        // Once a time  i got the username ,the user is searched through the username
         UserEntity userEntity= this.userRepository.getUserByEmail(username)
        .orElseThrow(()->new UsernameNotFoundException("User "+username+" Not found"));
        Long userId=userEntity.getIdUser();
        SongUser likedSong= songUserRepository.likedSong(userId, idSong);
        likedSong.setUser(null);
        likedSong.setIdUser(null);
        likedSong.setSong(null);
        return likedSong;
      }
  
    
}
