package com.standby.backend.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accesos_usuarios",schema="standby")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AccesosUsuarios {

    @Id
    @Column(name = "log_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idAcceso")
    private Acceso acceso;

    @Column(name = "activo")
    private boolean isActive;

    
}
