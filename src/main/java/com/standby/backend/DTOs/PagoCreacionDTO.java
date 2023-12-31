package com.standby.backend.DTOs;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PagoCreacionDTO {
    private String idUsuario;
    private String idAcceso;
    private BigDecimal cantidad;
}
