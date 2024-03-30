package com.datamusic.datamusic.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserRol {
    private Long idUser;
    private Long idRol;
     @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserEntity user;
    private Rol rol;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
