package com.standby.backend.DTOs;

import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResidencialCreacionDTO {
    private String direccion;

    @Column(nullable = true)
    private String nombre;
    
    @Column(unique = true)
    private String numeroSerie;
    
    private String latitudResidencial;
    
    private String longitudResidencial;
    private int radio;
}
