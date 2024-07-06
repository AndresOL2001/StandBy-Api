package com.standby.backend.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.standby.backend.DTOs.creation.LogCreacionDTO;
import com.standby.backend.models.Log;

public interface ILogService {

    public Log crearLog(LogCreacionDTO logCreacionDTO,boolean invitado);

    public List<Log> obtenerLogsPorResidencial(UUID idResidencial);

    public List<Log> obtenerLogs();

    public Log cerrarLog(UUID logId);


}
