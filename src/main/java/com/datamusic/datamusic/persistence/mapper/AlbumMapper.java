package com.datamusic.datamusic.persistence.mapper;

import java.util.List;

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
      @Mapping(source = "genero.idGenero", target = "gender.genderId"),
      @Mapping(source = "genero.nombre", target = "gender.name"),
      @Mapping(target = "gender.album",ignore=true),
  })
  Album toAlbum(AlbumEntity albumEntity);

  List<Album> toAlbum(List<AlbumEntity> albums);

  @InheritInverseConfiguration
   @Mappings({
      @Mapping(target = "genero.albums",ignore=true),
  })
  AlbumEntity toAlbumEntity(Album album);
}
