package com.standby.backend.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
public class Residencial {
    @Id
    private UUID idResidencial;

    private String direccion;
    
    @Column(unique = true)
    private String numeroSerie;
    
    private String latitudResidencial;
    
    private String longitudResidencial;
    
    @OneToMany(mappedBy = "residencial", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Usuario> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "residencial", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Acceso> accesos = new HashSet<>();

    public Residencial(UUID idResidencial) {
        this.idResidencial = idResidencial;
    }
}
