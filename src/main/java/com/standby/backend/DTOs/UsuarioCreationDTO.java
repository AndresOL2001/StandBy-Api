package com.standby.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreationDTO {
    private String numeroSerie;
    private String nombreCompleto;
    private String calle;
    private String numeroCasa;
    private String celular;
    private String contraseña;
}
