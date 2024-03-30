package com.datamusic.datamusic.domain;

import java.util.List;

import com.datamusic.datamusic.persistence.entity.UsuarioRol;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Rol {
    private Long idRol;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserRol> users;

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserRol> getUsers() {
        return users;
    }

    public void setUsers(List<UserRol> users) {
        this.users = users;
    }
    

}
