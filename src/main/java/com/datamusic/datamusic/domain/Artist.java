package com.datamusic.datamusic.domain;

import java.util.List;

public class Artist {
    private Long artistId;
    private String name;
    private List<AlbumArtist>albums;
    // private List<Album>albumsInfo;

    
    public Long getArtistId() {
        return artistId;
    }
    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<AlbumArtist> getAlbums() {
        return albums;
    }
    public void setAlbums(List<AlbumArtist> albums) {
        this.albums = albums;
    }
    // public List<Album> getAlbumsInfo() {
    //     return albumsInfo;
    // }
    // public void setAlbumsInfo(List<Album> albumsInfo) {
    //     this.albumsInfo = albumsInfo;
    // }
    

    

}
