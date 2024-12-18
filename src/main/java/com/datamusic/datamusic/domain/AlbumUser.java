package com.datamusic.datamusic.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

public class AlbumUser {
     @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long idUser;
    private Long idAlbum;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime dateAdd;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dateAddText;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserEntity user;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Album album;

    public Long getIdUser() {
        return idUser;
    }
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    public Long getIdAlbum() {
        return idAlbum;
    }
    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
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
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
    public Album getAlbum() {
        return album;
    }
    public void setAlbum(Album album) {
        this.album = album;
    }

    

    
}
