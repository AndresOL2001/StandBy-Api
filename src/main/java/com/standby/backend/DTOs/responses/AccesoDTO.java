package com.standby.backend.DTOs.responses;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.standby.backend.models.Residencial;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccesoDTO {
    private UUID idAcceso;
    private String LongitudCaseta;
    private String LatitudCaseta;
    private String direccion;

    private BigDecimal precio;

    private boolean garage;
    
    private String nombre;
    
    private int radio;

    private boolean isActive;
}
