package com.datamusic.datamusic.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SongUser {
    private Long idUser;
    private Long idSong;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime dateAdd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dateAddText;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserEntity user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Song song;
    
    public Long getIdUser() {
        return idUser;
    }
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    public Long getIdSong() {
        return idSong;
    }
    public void setIdSong(Long idSong) {
        this.idSong = idSong;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
    public Song getSong() {
        return song;
    }
    public void setSong(Song song) {
        this.song = song;
    }
    public LocalDateTime getDateAdd() {
        return dateAdd;
    }
    public void setDateAdd(LocalDateTime dateAdd) {
        this.dateAdd = dateAdd;
    }
    public String getDateAddText() {
        return dateAddText;
    }
    public void setDateAddText(String dateAddText) {
        this.dateAddText = dateAddText;
    }
    
    
}
