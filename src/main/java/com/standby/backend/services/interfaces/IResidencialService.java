package com.standby.backend.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.standby.backend.DTOs.ResidencialCreacionDTO;
import com.standby.backend.models.Residencial;

public interface IResidencialService {
    public Residencial obtenerResidencialPorId(UUID idResidencial);
    public List<Residencial> obtenerResidenciales();

    public Residencial guardarResidencial(ResidencialCreacionDTO residencial);
    public Residencial editarResidencial(ResidencialCreacionDTO residencial, UUID idResidencial);
    public void eliminarResidencial(UUID idResidencial);

}
