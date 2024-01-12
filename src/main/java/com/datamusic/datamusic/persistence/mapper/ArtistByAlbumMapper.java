package com.datamusic.datamusic.persistence.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.persistence.entity.AlbumsArtista;

@Mapper(componentModel = "spring", uses = { ArtistMapper.class })
public interface ArtistByAlbumMapper {

    @Mappings({
            @Mapping(source = "id.idAlbum", target = "albumId", ignore = true),
            @Mapping(source = "id.idArtista", target = "artistId", ignore = true),
            @Mapping(source = "album.idAlbum", target = "album.albumId", ignore = true),
            @Mapping(source = "album.nombre", target = "album.name", ignore = true),
            @Mapping(source = "album.anio", target = "album.year", ignore = true),
            @Mapping(source = "album.idGenero", target = "album.genderId", ignore = true),
            @Mapping(source = "album.genero", target = "album.gender", ignore = true),
            @Mapping(source = "album.genero.idGenero", target = "album.gender.genderId", ignore = true),
            @Mapping(source = "album.genero.albums", target = "album.gender.album", ignore = true),
            @Mapping(source = "album.genero.nombre", target = "album.gender.name", ignore = true),
            @Mapping(source = "album.artistas", target = "album.artists", ignore = true),
            @Mapping(source = "artista", target = "artist", ignore = false),

    })
    AlbumArtist toAlbumArtistByAlbum(AlbumsArtista albumsArtista);

    @AfterMapping
    default void removeFieldsNoNecesary(@MappingTarget AlbumArtist albumArtist) {
        albumArtist.setAlbum(null);
        albumArtist.getArtist().setAlbums(null);
    }

}
