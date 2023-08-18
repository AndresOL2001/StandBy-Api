package com.standby.backend.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.standby.backend.DTOs.AccesoCreacionDTO;
import com.standby.backend.models.Acceso;

public interface IAccesoService {
    public Acceso obtenerAccesoPorId(UUID idAcceso);
    public List<Acceso> obtenerAccesosPorIdResidencial(UUID idResidencial);
    public Acceso guardarAcceso(AccesoCreacionDTO accesoCreacionDTO);
    public Acceso editarAcceso(AccesoCreacionDTO accesoCreacionDTO,UUID idAcceso);
    public List<Acceso> obtenerAccesos();
    public Acceso cambiarResidencialAcceso(UUID idAcceso, UUID idResidencial);
    public Acceso guardarAccesoGarage(AccesoCreacionDTO accesoCreacionDTO);
    public List<Acceso> obtenerAccesosPorIdUsuario(UUID idUsuario);

}
