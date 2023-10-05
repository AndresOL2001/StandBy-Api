package com.standby.backend.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "standby")
public class Compartir {
    @Id
    private UUID idCompartir;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idUsuario")
    @JsonIgnore
    private Usuario usuario;

    private String link;

    private boolean estaActivo;
    private LocalDateTime fecha_creacion;
    private LocalDateTime expiracion;

    public boolean hasExpired() {
        // Check if the current time is after the expiration time
        return LocalDateTime.now().isAfter(expiracion);
    }

}
