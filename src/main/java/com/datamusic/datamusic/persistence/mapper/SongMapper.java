package com.datamusic.datamusic.persistence.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.Song;
import com.datamusic.datamusic.persistence.entity.Cancion;

@Mapper(componentModel = "spring", uses = { AlbumMapper.class })
public interface SongMapper {
  @Mappings({
      @Mapping(source = "idAlbum", target = "albumId"),
      @Mapping(source = "nombre", target = "name"),
      @Mapping(source = "duracion", target = "duration"),
      @Mapping(source = "idCancion", target = "songId"),
      @Mapping(source = "album", target = "album"),
  })
  Song toSong(Cancion cancion);

  List<Song> toSong(List<Cancion> canciones);

  @InheritInverseConfiguration
  Cancion toCancion(Song song);

}
