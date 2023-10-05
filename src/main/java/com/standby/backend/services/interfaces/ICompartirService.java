package com.standby.backend.services.interfaces;

import com.standby.backend.DTOs.creation.CompartirCreacionDTO;
import com.standby.backend.models.Compartir;

public interface ICompartirService {
        public Compartir crearCompartida(CompartirCreacionDTO compartir);

}
