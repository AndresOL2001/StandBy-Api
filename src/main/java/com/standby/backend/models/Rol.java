package com.standby.backend.models;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
public class Rol {
    @Id
    private int IdRol;
    
    private String name;

    @OneToMany(mappedBy = "rol",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Usuario> usuarios = new HashSet<>();
    
    public Rol(int idRol){
        this.IdRol = idRol;
    }
}
