package com.datamusic.datamusic.persistence.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.persistence.entity.AlbumEntity;

@Mapper(componentModel = "spring",uses = {ArtistByAlbumMapper.class})
public interface AlbumMapper {
  @Mappings({
      @Mapping(source = "idAlbum", target = "albumId"),
      @Mapping(source = "nombre", target = "name"),
      @Mapping(source = "anio", target = "year"),
      @Mapping(source = "idGenero", target = "genderId"),
      @Mapping(source = "genero.idGenero", target = "gender.genderId"),
      @Mapping(source = "genero.nombre", target = "gender.name"),
      @Mapping(target = "gender.album", ignore = true),
      @Mapping(source = "artistas", target = "artists"),

  })
  Album toAlbum(AlbumEntity albumEntity);

  List<Album> toAlbum(List<AlbumEntity> albums);

  @AfterMapping
  default void removeFieldsNoNecesary(@MappingTarget Album album) {
    // if (album.getArtists()!=null) {
      // album.getArtists().forEach(artist -> {
      //   artist.setAlbum(null);
      // });
    // }
  }

  @InheritInverseConfiguration
  @Mappings({
      @Mapping(target = "genero.albums", ignore = true),
  })
  AlbumEntity toAlbumEntity(Album album);
}
