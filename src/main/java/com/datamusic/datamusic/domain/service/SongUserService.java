package com.datamusic.datamusic.domain.service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamusic.datamusic.domain.SongUser;
import com.datamusic.datamusic.domain.repository.SongUserRepository;

@Service
public class SongUserService {

    @Autowired
    private SongUserRepository songUserRepository;

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
    
}
