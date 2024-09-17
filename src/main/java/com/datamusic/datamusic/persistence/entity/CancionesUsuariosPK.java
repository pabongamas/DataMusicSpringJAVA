package com.datamusic.datamusic.persistence.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CancionesUsuariosPK implements Serializable {

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_cancion")
    private Long idCancion;

    public CancionesUsuariosPK() {
    }

    public CancionesUsuariosPK(Long idUsuario, Long idCancion) {
        this.idUsuario = idUsuario;
        this.idCancion = idCancion;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(Long idCancion) {
        this.idCancion = idCancion;
    }

}
