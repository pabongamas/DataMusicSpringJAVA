package com.datamusic.datamusic.domain;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Album {
    private Long albumId;
    @NotNull(message = "El nombre no debe ser nulo")
    @NotBlank(message = "El nombre no debe ir vacio")
    private String name;
    @NotNull(message = "El Año no debe ser nulo")
    private Integer year;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(message = "El Genero no debe ser nulo")
    private Long genderId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Gender gender;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AlbumArtist> artists;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nameFile;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cover;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private byte[] imgAlbum;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String pathImageAlbum;

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

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public byte[] getImgAlbum() {
        return imgAlbum;
    }

    public void setImgAlbum(byte[] imgAlbum) {
        this.imgAlbum = imgAlbum;
    }

    public String getPathImageAlbum() {
        return pathImageAlbum;
    }

    public void setPathImageAlbum(String pathImageAlbum) {
        this.pathImageAlbum = pathImageAlbum;
    }
    
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        result = prime * result + ((genderId == null) ? 0 : genderId.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((artists == null) ? 0 : artists.hashCode());
        result = prime * result + ((nameFile == null) ? 0 : nameFile.hashCode());
        result = prime * result + ((cover == null) ? 0 : cover.hashCode());
        result = prime * result + Arrays.hashCode(imgAlbum);
        result = prime * result + ((pathImageAlbum == null) ? 0 : pathImageAlbum.hashCode());
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
        Album other = (Album) obj;
        if (albumId == null) {
            if (other.albumId != null)
                return false;
        } else if (!albumId.equals(other.albumId))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        if (genderId == null) {
            if (other.genderId != null)
                return false;
        } else if (!genderId.equals(other.genderId))
            return false;
        if (gender == null) {
            if (other.gender != null)
                return false;
        } else if (!gender.equals(other.gender))
            return false;
        if (artists == null) {
            if (other.artists != null)
                return false;
        } else if (!artists.equals(other.artists))
            return false;
        if (nameFile == null) {
            if (other.nameFile != null)
                return false;
        } else if (!nameFile.equals(other.nameFile))
            return false;
        if (cover == null) {
            if (other.cover != null)
                return false;
        } else if (!cover.equals(other.cover))
            return false;
        if (!Arrays.equals(imgAlbum, other.imgAlbum))
            return false;
        if (pathImageAlbum == null) {
            if (other.pathImageAlbum != null)
                return false;
        } else if (!pathImageAlbum.equals(other.pathImageAlbum))
            return false;
        return true;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

}
