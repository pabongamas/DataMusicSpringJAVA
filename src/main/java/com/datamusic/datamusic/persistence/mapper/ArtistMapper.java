package com.datamusic.datamusic.persistence.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.Artist;
import com.datamusic.datamusic.persistence.entity.Artista;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
      @Mappings({
        @Mapping(source="idArtista",target="artistId"),
        @Mapping(source="nombre",target="name"),
    })
    Artist toArtist(Artista artista);
    List<Artist> toArtists(List<Artista>artistas);

    @InheritInverseConfiguration
    Artista toArtista(Artist artist);
}
