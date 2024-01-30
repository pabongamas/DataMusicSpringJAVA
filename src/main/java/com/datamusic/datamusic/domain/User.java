package com.datamusic.datamusic.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class User {

    private Long idUser;
    @NotNull(message = "El nombre no debe ser nulo")
    @NotBlank(message = "El nombre no debe ir vacio")
    private String name;
    @NotNull(message = "El Apellido no debe ser nulo")
    @NotBlank(message = "El Apellido no debe ir vacio")
    private String lastnames;
    @NotNull(message = "El Email no debe ser nulo")
    @NotBlank(message = "El Email no debe ir vacio")
    @Email(message = "El email debe tener un formato valido")
    private String email;
    @NotNull(message = "La Contraseña no debe ser nula")
    @NotBlank(message = "La Contraseña no debe ir vacio")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastnames() {
        return lastnames;
    }

    public void setLastnames(String lastnames) {
        this.lastnames = lastnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
