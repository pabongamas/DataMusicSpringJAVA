package com.datamusic.datamusic.persistence.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.AlbumUser;
import com.datamusic.datamusic.persistence.entity.AlbumsUsuario;
import java.util.List;

@Mapper(componentModel = "spring", uses = {  AlbumMapper.class ,UserMapper.class})
public interface AlbumUserMapper {

    @Mappings({
            @Mapping(source = "id.idAlbum", target = "idAlbum"),
            @Mapping(source = "id.idUsuario", target = "idUser"),
            @Mapping(source = "usuario", target = "user", ignore = false),
            @Mapping(source = "album", target = "album", ignore = false),
    })
    AlbumUser toAlbumAUser(AlbumsUsuario albumsUsuario);

    List<AlbumUser> toAlbumAUser(List<AlbumsUsuario> albumsUsuario);


    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "album", ignore = true),
            @Mapping(target = "usuario", ignore = true)
    })
    AlbumsUsuario toAlbumsUsuario(AlbumUser albumArtiAlbumUserst);

}
