package com.standby.backend.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.standby.backend.models.Acceso;
import com.standby.backend.repositories.AccesoRepository;

@Service
public class GarageService {
    private final AccesoRepository accesoRepository;

    public GarageService(AccesoRepository accesoRepository) {
        this.accesoRepository = accesoRepository;
    }
    public List<Acceso> obtenerGarages(){
        return accesoRepository.findAll().stream().filter(Acceso::isGarage).collect(Collectors.toList());
    }
}
