package com.standby.backend.services.interfaces;

import java.util.UUID;

import com.standby.backend.DTOs.ResidencialCreacionDTO;
import com.standby.backend.models.Residencial;

public interface IResidencialService {
     public Residencial obtenerResidencialPorId(UUID idResidencial);
    public Residencial guardarResidencial(ResidencialCreacionDTO residencial);
}
