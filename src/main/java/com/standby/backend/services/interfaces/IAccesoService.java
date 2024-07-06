package com.standby.backend.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.standby.backend.DTOs.ErrorMessage;
import com.standby.backend.DTOs.VerificarLinkCompartir;
import com.standby.backend.DTOs.creation.AccesoCreacionDTO;
import com.standby.backend.DTOs.responses.AccesoDTO;
import com.standby.backend.models.Acceso;
import com.standby.backend.models.Usuario;

public interface IAccesoService {
    public Acceso obtenerAccesoPorId(UUID idAcceso);
    public Usuario comprarCompartidas(UUID idUsuario);

    public List<Acceso> obtenerAccesosPorIdResidencial(UUID idResidencial);

    public Acceso guardarAcceso(AccesoCreacionDTO accesoCreacionDTO);

    public Acceso editarAcceso(AccesoCreacionDTO accesoCreacionDTO, UUID idAcceso);

    public List<Acceso> obtenerAccesos();

    public Acceso cambiarResidencialAcceso(UUID idAcceso, UUID idResidencial);

    public Acceso guardarAccesoGarage(AccesoCreacionDTO accesoCreacionDTO);

    public ErrorMessage verificarUsuarioPagos(UUID idUsuario);

    public List<AccesoDTO> obtenerAccesosPorIdUsuario(UUID idUsuario);

    public void verificarAccesoCompartidas(VerificarLinkCompartir verificarLinkCompartir);

}
