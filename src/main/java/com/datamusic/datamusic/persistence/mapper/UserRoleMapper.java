package com.datamusic.datamusic.persistence.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.UserRol;
import com.datamusic.datamusic.persistence.entity.UsuarioRol;

@Mapper(componentModel = "spring",uses = {RolMapper.class})
public interface UserRoleMapper {
    @Mappings({
        @Mapping(source = "id.idUsuario", target = "idUser"),
        @Mapping(source = "id.idRol", target = "idRol"),
        @Mapping(source = "usuario", target = "user"),
    })
    UserRol toUserRol(UsuarioRol usuarioRol);

    List<UserRol> toUserRol(List<UsuarioRol> usuarioRol);
}
