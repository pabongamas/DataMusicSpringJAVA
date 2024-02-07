package com.datamusic.datamusic.persistence.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

import com.datamusic.datamusic.domain.User;
import com.datamusic.datamusic.persistence.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mappings({
      @Mapping(source = "idUsuario", target = "idUser"),
      @Mapping(source = "nombre", target = "name"),
      @Mapping(source = "apellidos", target = "lastnames"),
      @Mapping(source = "correoElectronico", target = "email"),
      @Mapping(source = "contrasena", target = "password"),
  })
  User toUser(Usuario usuario);

  List<User> toUser(List<Usuario> usuarios);

  @AfterMapping
  default void removeFieldsNoNecesary(@MappingTarget User user) {
    // if (gender.getAlbum() != null) {
    //   gender.getAlbum().forEach(album -> {
    //     album.setGender(null);
    //     album.setGenderId(null);
    //     album.getArtists().forEach(artist -> {
    //       artist.setAlbum(null);
    //     });
    //   });
    // }
    user.setPassword(null);
  }

  @InheritInverseConfiguration
  Usuario toUsuario(User user);
}
