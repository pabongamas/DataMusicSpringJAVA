package com.datamusic.datamusic.persistence.DTO;

public class FormAdminSaveSong {
    private Long songId;
    private String name;
    private Long duration;
    private Long albumId;
    private Long numberSong;
    private Boolean explicit;
    private Boolean edited;
    private Boolean loaded;
    private String nameFile;

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
    public Long getDuration() {
        return duration;
    }
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    public Long getAlbumId() {
        return albumId;
    }
    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
    public Long getNumberSong() {
        return numberSong;
    }
    public void setNumberSong(Long numberSong) {
        this.numberSong = numberSong;
    }
    public Boolean getExplicit() {
        return explicit;
    }
    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
    }
    public Boolean getEdited() {
        return edited;
    }
    public void setEdited(Boolean edited) {
        this.edited = edited;
    }
    public Boolean getLoaded() {
        return loaded;
    }
    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }
    public String getNameFile() {
        return nameFile;
    }
    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
    
}
