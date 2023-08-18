package com.standby.backend.services.implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.standby.backend.DTOs.ResidencialCreacionDTO;
import com.standby.backend.helpers.Mapper;
import com.standby.backend.models.Residencial;
import com.standby.backend.repositories.ResidencialRepository;
import com.standby.backend.services.interfaces.IResidencialService;

@Service
public class ResidencialService implements IResidencialService {

    private final ResidencialRepository residencialRepository;
    private final Mapper mapper;

    public ResidencialService(ResidencialRepository residencialRepository, Mapper mapper) {
        this.residencialRepository = residencialRepository;
        this.mapper = mapper;
    }

    @Override
    public Residencial obtenerResidencialPorId(UUID idResidencial) {
        Optional<Residencial> rOptional = this.residencialRepository.findById(idResidencial);
        if (rOptional.isEmpty()) {
            throw new RuntimeException("Error: La residencial con el id: " + idResidencial + " no existe");
        }
        return rOptional.get();
    }

    @Override
    public Residencial guardarResidencial(ResidencialCreacionDTO residencial) {
        Optional<Residencial> rOptional = this.residencialRepository.findByNumeroSerie(residencial.getNumeroSerie());
        if (rOptional.isPresent()) {
            throw new RuntimeException(
                    "Error: La residencial con el id: " + residencial.getNumeroSerie() + " ya existe");
        }
        return residencialRepository.save(mapper.residencialCreationDTOtoUsuario(residencial));
    }

    @Override
    public Residencial editarResidencial(ResidencialCreacionDTO residencial, UUID idResidencial) {
        Optional<Residencial> rOptional = this.residencialRepository.findById(idResidencial);
        if (rOptional.isEmpty()) {
            throw new RuntimeException("Error: La residencial con el id: " + idResidencial + " no existe");
        }
        rOptional.get().setDireccion(residencial.getDireccion());
        rOptional.get().setNombre(residencial.getNombre());
        rOptional.get().setLatitudResidencial(residencial.getLatitudResidencial());
        rOptional.get().setLongitudResidencial(residencial.getLongitudResidencial());
        rOptional.get().setNumeroSerie(residencial.getNumeroSerie());
        rOptional.get().setRadio(residencial.getRadio());

        return residencialRepository.save(rOptional.get());
    }

    @Override
    public List<Residencial> obtenerResidenciales() {
        return this.residencialRepository.findAll();
    }

    @Override
    public void eliminarResidencial(UUID idResidencial) {
        residencialRepository.deleteById(idResidencial);
    }

}
