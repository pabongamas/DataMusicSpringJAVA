package com.datamusic.datamusic.persistence.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.AlbumArtist;
import com.datamusic.datamusic.persistence.entity.AlbumsArtista;

@Mapper(componentModel = "spring")
public interface AlbumArtistMapper {

        @Mappings({
                        @Mapping(source = "id.idAlbum", target = "albumId"),
                        @Mapping(source = "id.idArtista", target = "artistId"),
                        @Mapping(source = "album.idAlbum", target = "album.albumId"),
                        @Mapping(source = "album.nombre", target = "album.name"),
                        @Mapping(source = "album.anio", target = "album.year"),
                        @Mapping(source = "album.idGenero", target = "album.genderId"),
                        @Mapping(source = "album.genero", target = "album.gender"),
                        @Mapping(source = "album.genero.idGenero", target = "album.gender.genderId"),
                        @Mapping(source = "album.genero.nombre", target = "album.gender.name"),
                        // @Mapping(source="artista.nombre",target = "artist.name") // Ignora la propiedad "artists"
        })
        AlbumArtist toAlbumArtist(AlbumsArtista albumsArtista);
        // @AfterMapping
        // default void removeArtistField(@MappingTarget AlbumArtist albumArtist) {
        // albumArtist.setArtist(null);
//     }
        

        List<AlbumArtist> toAlbumArtist(List<AlbumsArtista> albumsArtista);

        @InheritInverseConfiguration
        @Mappings({
                        @Mapping(target = "album", ignore = true),
                        @Mapping(target = "artista", ignore = true)
        })
        AlbumsArtista toAlbumsArtista(AlbumArtist albumArtist);
}
