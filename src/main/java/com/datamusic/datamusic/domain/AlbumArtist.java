package com.datamusic.datamusic.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;

public class AlbumArtist {
    @NotNull(message = "El albumId no debe ser nulo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long albumId;
    @NotNull(message = "El artistId no debe ser nulo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long artistId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Album album;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Artist artist;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
