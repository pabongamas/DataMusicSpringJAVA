package com.datamusic.datamusic.domain;

import java.util.List;


public class Gender {
    private Long genderId;
    private String name;
    private List<Album>  album;

    public Long getGenderId() {
        return genderId;
    }
    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Album> getAlbum() {
        return album;
    }
    public void setAlbum(List<Album> album) {
        this.album = album;
    }
    

    

    
}
