package com.datamusic.datamusic.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.SongPlaylist;
import com.datamusic.datamusic.persistence.entity.CancionPlaylist;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SongMapper.class,PlaylistMapper.class})
public interface SongPlaylistMapMapper {


    @Mappings({
         @Mapping(source = "id.idCancion", target = "songId"),
         @Mapping(source = "id.idPlaylist", target = "idPlaylist"),
         @Mapping(source = "cancion", target = "song", ignore = false),
         @Mapping(source = "playlistEntity", target = "playlist", ignore = false),
    })
    SongPlaylist toSongPlaylistMap(CancionPlaylist cancionPlaylist);
    
    List<SongPlaylist> toSongPlaylistMap(List<CancionPlaylist> cancionPlaylist);

}
