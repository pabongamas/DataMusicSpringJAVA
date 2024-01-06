package com.datamusic.datamusic.persistence.mapper;

import java.util.List;
import java.util.Locale.Category;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.persistence.entity.AlbumEntity;

@Mapper(componentModel = "spring")
public interface AlbumMapper {
     @Mappings({
            @Mapping(source = "idAlbum", target = "albumId"),
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "anio", target = "year"),
            @Mapping(source = "idGenero", target = "genderId"),
            @Mapping(source = "genero", target = "gender"),
    })
    Album toAlbum(AlbumEntity albumEntity);

      List<Album> toAlbum(List<AlbumEntity>albums);

    @InheritInverseConfiguration
    //  @Mappings({
    //         @Mapping(source = "idAlbum", target = "albumId"),
    // })
    AlbumEntity toAlbumEntity(Album album);
}
