package com.datamusic.datamusic.domain.projection;


public class SummaryPlaylistSong {
    private Long songId;

    private String name;

    private String duration;

    private Long albumId;

    private String nameAlbum;

    private String yearAlbum;



    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }

    public String getYearAlbum() {
        return yearAlbum;
    }

    public void setYearAlbum(String yearAlbum) {
        this.yearAlbum = yearAlbum;
    }

    

   

    
    
}

