package com.datamusic.datamusic.persistence.DTO;
import java.util.List;

public class AlbumResponseDTO {
    private Long albumId;
    private String name;
    private int year;
    private Long genderId;
    private String genderName;
    private List<ArtistDTO> artists;
    
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
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public Long getGenderId() {
        return genderId;
    }
    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }
    public String getGenderName() {
        return genderName;
    }
    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }
    public List<ArtistDTO> getArtists() {
        return artists;
    }
    public void setArtists(List<ArtistDTO> artists) {
        this.artists = artists;
    }

    
}
