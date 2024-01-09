package com.datamusic.datamusic.persistence.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.Gender;
import com.datamusic.datamusic.persistence.entity.AlbumEntity;
import com.datamusic.datamusic.persistence.entity.Genero;

@Mapper(componentModel = "spring",uses = {AlbumMapper.class})
public interface GenderMapper {
      @Mappings({
        @Mapping(source="idGenero",target="genderId"),
        @Mapping(source="nombre",target="name"),
        @Mapping(source="albums",target="album"),
    })
    Gender toGender(Genero genero);
    List<Gender> toGenders(List<Genero>generos);

    @InheritInverseConfiguration
    Genero toGenero(Gender gender);

     // Mapeo inverso de List<AlbumEntity> a List<Album>
    @InheritInverseConfiguration
    List<AlbumEntity> toAlbumEntities(List<Album> albums);
    
}
