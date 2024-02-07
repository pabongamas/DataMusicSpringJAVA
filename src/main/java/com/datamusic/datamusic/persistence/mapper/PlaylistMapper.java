package com.datamusic.datamusic.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.Album;
import com.datamusic.datamusic.domain.Playlist;
import com.datamusic.datamusic.persistence.entity.AlbumEntity;
import com.datamusic.datamusic.persistence.entity.PlaylistEntity;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface PlaylistMapper {

    @Mappings({
            @Mapping(source = "idPlaylist", target = "idPlaylist"),
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "fechaCreacion", target = "createdDate"),
            @Mapping(source = "idUsuario", target = "idUser"),
            @Mapping(source = "usuario", target = "user"),
    })
    Playlist toPlaylist(PlaylistEntity playlistEntity);

    List<Playlist> toPlaylists(List<PlaylistEntity> playlists);
}
