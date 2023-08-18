package com.standby.backend.DTOs;

import java.math.BigDecimal;

import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccesoCreacionDTO {
    private String idResidencial;
    private String latitudCaseta;
    private String longitudCaseta;
    private String direccion;
    private BigDecimal precio;
    private String nombre;
    private boolean garage;
    private int radio;
    private String usuarioId;
}
