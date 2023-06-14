package com.standby.backend.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Acceso {
    @Id
    private UUID idAcceso;

    private String LongitudCaseta;
    private String LatitudCaseta;
    private String direccion;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idResidencial")
    @JsonIgnore
    private Residencial residencial;
}
