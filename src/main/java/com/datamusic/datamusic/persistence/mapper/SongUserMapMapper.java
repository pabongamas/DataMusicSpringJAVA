package com.datamusic.datamusic.persistence.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.SongPlaylist;
import com.datamusic.datamusic.domain.SongUser;
import com.datamusic.datamusic.persistence.entity.CancionPlaylist;
import com.datamusic.datamusic.persistence.entity.CancionesUsuarios;

@Mapper(componentModel = "spring", uses = { SongMapper.class, UserMapper.class })
public interface SongUserMapMapper {
     @Mappings({
            @Mapping(source = "id.idCancion", target = "idSong"),
            @Mapping(source = "id.idUsuario", target = "idUser"),
            @Mapping(source="fecha_agregada",target = "dateAdd"),
            @Mapping(source = "cancion", target = "song", ignore = false),
            @Mapping(source = "usuario", target = "user", ignore = false),
    })
    SongUser toSongUserMap(CancionesUsuarios cancionesUsuarios);

    List<SongUser> toSongUserMap(List<CancionesUsuarios> cancionesUsuarios);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "cancion", ignore = true),
            @Mapping(target = "usuario", ignore = true)
    })
    CancionesUsuarios toCancionesUsuarios(SongUser songUser);
}
