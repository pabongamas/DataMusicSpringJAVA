package com.datamusic.datamusic.domain;

import java.util.List;

public class Album {
    private Long albumId;
    private String name;
    private Integer year;
    private Long genderId;
    private Gender gender;
    private List<AlbumArtist>artists;

    
    public Long getAlbumId() {
        return albumId;
    }
    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Long getGenderId() {
        return genderId;
    }
    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public List<AlbumArtist> getArtists() {
        return artists;
    }
    public void setArtists(List<AlbumArtist> artists) {
        this.artists = artists;
    }

    
    

    
}
