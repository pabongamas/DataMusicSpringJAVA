package com.datamusic.datamusic.persistence.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.datamusic.datamusic.domain.Rol;
import com.datamusic.datamusic.domain.UserEntity;
import com.datamusic.datamusic.persistence.entity.RolEntity;
import com.datamusic.datamusic.persistence.entity.Usuario;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper {

    @Mappings({
            @Mapping(source = "idRol", target = "idRol"),
            @Mapping(source = "nombre", target = "name"),
    })
    Rol toRol(RolEntity rol);

    List<Rol> toRol(List<RolEntity> rols);

    @InheritInverseConfiguration
    RolEntity toRolEntity(Rol rol);

}
