package com.standby.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerificarLinkCompartir {
    private String idUsuario;
    private String idResidencial;
    private String idCompartir;
    private String idAcceso;
}
