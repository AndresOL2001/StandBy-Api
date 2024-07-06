package com.standby.backend.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.standby.backend.DTOs.creation.LogCreacionDTO;
import com.standby.backend.models.Log;

import com.standby.backend.services.implementation.LogService;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "*")
public class LogsController {
     private final LogService logService;

    public LogsController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping()
    public Log crearLog(@RequestBody LogCreacionDTO logCreacionDTO){

        return logService.crearLog(logCreacionDTO,false);

    }

    @GetMapping("/{idResidencial}")
    public List<Log> obtenerLogsPorIdResidencial(@PathVariable String idResidencial){

        return logService.obtenerLogsPorResidencial(UUID.fromString(idResidencial));

    }

    @GetMapping()
    public List<Log> obtenerLogs(){

        return logService.obtenerLogs();

    }

    @PostMapping("/{logId}")
    public Log cerrarLog(@PathVariable String logId){

        return logService.cerrarLog(UUID.fromString(logId));

    }


}
