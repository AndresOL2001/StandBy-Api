package com.standby.backend.models;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Usuario {
    @Id
    private UUID idUsuario;
    private String nombreCompleto;
    private String calle;
    private String numeroCasa;
    private String celular;
    private String contraseña;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "id_rol")
    @JsonIgnore
    private Rol rol;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idResidencial")
    @JsonIgnore
    private Residencial residencial;
}