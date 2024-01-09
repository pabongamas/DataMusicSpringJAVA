package com.datamusic.datamusic.persistence.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.persistence.entity.AlbumsArtista;

@Mapper(componentModel = "spring")
public interface AlbumArtistMapper {
    
     @Mappings({
            @Mapping(source = "id.idAlbum", target = "albumId"),
            @Mapping(source = "id.idArtista", target = "artistId")
    })
    AlbumArtist toAlbumArtist(AlbumsArtista albumsArtista);

    @InheritInverseConfiguration
     @Mappings({
            @Mapping(target = "album",ignore = true),
            @Mapping(target = "artista",ignore = true)
    })
    AlbumsArtista toAlbumsArtista(AlbumArtist albumArtist);
}
