package com.datamusic.datamusic.persistence.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.Artist;
import com.datamusic.datamusic.persistence.entity.Artista;

@Mapper(componentModel = "spring", uses = { AlbumArtistMapper.class, AlbumMapper.class })
public interface ArtistMapper {
  @Mappings({
      @Mapping(source = "idArtista", target = "artistId"),
      @Mapping(source = "nombre", target = "name"),
      @Mapping(source = "albums", target = "albums"),
      // @Mapping(source="albumsInformacion",target="albumsInfo"),
  })
  Artist toArtist(Artista artista);

  List<Artist> toArtists(List<Artista> artistas);

  @AfterMapping
  default void removeFieldsNoNecesary(@MappingTarget Artist artist) {
    if(artist.getAlbums()!=null){
      artist.getAlbums().forEach(artistValue->{
        artistValue.setArtistId(null);
      });
    }
  }

  @InheritInverseConfiguration
  Artista toArtista(Artist artist);
}
