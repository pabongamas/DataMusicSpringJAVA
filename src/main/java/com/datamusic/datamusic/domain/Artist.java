package com.datamusic.datamusic.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Artist {
    private Long artistId;
    @NotNull(message="El nombre no debe ser nulo")
    @NotBlank(message = "El nombre no debe ir vacio")
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AlbumArtist> albums;
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
    // return albumsInfo;
    // }
    // public void setAlbumsInfo(List<Album> albumsInfo) {
    // this.albumsInfo = albumsInfo;
    // }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((artistId == null) ? 0 : artistId.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((albums == null) ? 0 : albums.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Artist other = (Artist) obj;
        if (artistId == null) {
            if (other.artistId != null)
                return false;
        } else if (!artistId.equals(other.artistId))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (albums == null) {
            if (other.albums != null)
                return false;
        } else if (!albums.equals(other.albums))
            return false;
        return true;
    }

    
}
