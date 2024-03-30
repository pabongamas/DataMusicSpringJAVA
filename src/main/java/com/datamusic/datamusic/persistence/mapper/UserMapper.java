package com.datamusic.datamusic.persistence.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

import com.datamusic.datamusic.domain.UserEntity;
import com.datamusic.datamusic.persistence.entity.Usuario;

@Mapper(componentModel = "spring",uses = {UserRoleMapper.class})
public interface UserMapper {

  @Mappings({
      @Mapping(source = "idUsuario", target = "idUser"),
      @Mapping(source = "nombre", target = "name"),
      @Mapping(source = "apellidos", target = "lastnames"),
      @Mapping(source = "correoElectronico", target = "email"),
      @Mapping(source = "contrasena", target = "password"),
      @Mapping(source = "roles", target = "rols"),

  })
  UserEntity toUser(Usuario usuario);

  List<UserEntity> toUser(List<Usuario> usuarios);

  @AfterMapping
  default void removeFieldsNoNecesary(@MappingTarget UserEntity user) {
    // if (gender.getAlbum() != null) {
    //   gender.getAlbum().forEach(album -> {
    //     album.setGender(null);
    //     album.setGenderId(null);
    //     album.getArtists().forEach(artist -> {
    //       artist.setAlbum(null);
    //     });
    //   });
    // }
    // user.setPassword(null);
  }

  @InheritInverseConfiguration
  Usuario toUsuario(UserEntity user);
}
