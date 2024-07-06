package com.standby.backend.services.implementation;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.standby.backend.DTOs.creation.LogCreacionDTO;
import com.standby.backend.models.Acceso;
import com.standby.backend.models.Log;
import com.standby.backend.models.Usuario;
import com.standby.backend.repositories.AccesoRepository;
import com.standby.backend.repositories.LogRepository;
import com.standby.backend.repositories.UsuarioRepository;
import com.standby.backend.services.interfaces.ILogService;

@Service
public class LogService implements ILogService{

    private final LogRepository logRepository;
    private final AccesoRepository accesoRepository;
    private final UsuarioRepository usuarioRepository;
    public LogService(LogRepository logRepository,AccesoRepository accesoRepository,UsuarioRepository usuarioRepository) {
        this.logRepository = logRepository;
        this.accesoRepository = accesoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Log crearLog(LogCreacionDTO logCreacionDTO,boolean invitado) {

        Usuario usuario = usuarioRepository.findById(UUID.fromString(logCreacionDTO.getIdUsuario())).get();
        Acceso acceso = accesoRepository.findById(UUID.fromString(logCreacionDTO.getIdAcceso())).get();

        Log log = new Log();
        log.setAcceso(new Acceso(UUID.fromString(logCreacionDTO.getIdAcceso())));
        log.setUsuario(new Usuario(UUID.fromString(logCreacionDTO.getIdUsuario())));
        log.setId(UUID.randomUUID());
        if(invitado){
            log.setMensaje("Invitado de : "+usuario.getNombreCompleto() + ", "+acceso.getNombre()+", "+ new Date());
        }else{
            log.setMensaje("Log: "+usuario.getNombreCompleto() + ", "+acceso.getNombre()+", "+ new Date());
        }

        return logRepository.save(log);
    }

    @Override
    public List<Log> obtenerLogsPorResidencial(UUID idResidencial) {
        return logRepository.obtenerLogsPorResidencial(idResidencial);
    }

    @Override
    public List<Log> obtenerLogs() {
        Pageable pageable = PageRequest.of(0, 50);
        return logRepository.findLatestLogs(pageable);
        }

    @Override
    @Transactional
    public Log cerrarLog(UUID logId) {
        Log log = logRepository.findById(logId).orElseThrow(() -> new RuntimeException("Error id inexistente"));
        log.setEntro(true);
        return logRepository.save(log);

    }
    
}
