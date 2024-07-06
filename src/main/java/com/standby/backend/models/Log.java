package com.standby.backend.models;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "logs",schema="standby")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Log {

    @Id
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "idAcceso")
    @JsonIgnore
    private Acceso acceso; 

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    @JsonIgnore
    private Usuario usuario;

    private String mensaje;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = true, updatable = false)
    private Date fechaCreacion;

    @Column(nullable = true)
    private boolean entro;
}
