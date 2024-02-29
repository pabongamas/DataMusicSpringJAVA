package com.datamusic.datamusic.persistence.mapper.mappersSummary;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import com.datamusic.datamusic.domain.projection.SummaryPlaylistSong;
import com.datamusic.datamusic.persistence.projection.PlaylistSongsSummary;

@Mapper(componentModel = "spring")
public interface PlaylistSongsSummaryMapper {

    @Mappings({
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "duracion", target = "duration"),
            @Mapping(source = "id_Album", target = "albumId"),
            @Mapping(source = "id_Cancion", target = "songId"),
            @Mapping(source = "nombre_Album", target = "nameAlbum"),
            @Mapping(source = "anio_Album", target = "yearAlbum"),
    })
    SummaryPlaylistSong toSummaryPlaylistSong(PlaylistSongsSummary playlistSongsSummary);

    List<SummaryPlaylistSong> toSummaryPlaylistsSong(List<PlaylistSongsSummary> playlistSongsSummary);


   
    // Page<SummaryPlaylistSong> toSummaryPlaylistsSongPageable(Page<PlaylistSongsSummary> playlistSongsSummary);


}
