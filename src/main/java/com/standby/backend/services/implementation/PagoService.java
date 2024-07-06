package com.standby.backend.services.implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.standby.backend.DTOs.PagoCreacionDTO;
import com.standby.backend.helpers.Mapper;
import com.standby.backend.models.AccesosUsuarios;
import com.standby.backend.models.Pago;
import com.standby.backend.repositories.AccesosUsuariosRepository;
import com.standby.backend.repositories.PagoRepository;
import com.standby.backend.services.interfaces.IPagoService;

@Service
public class PagoService implements IPagoService{

    private final PagoRepository pagoRepository;
    private final AccesosUsuariosRepository accesosUsuariosRepository;
    private final Mapper mapper;

    public PagoService(PagoRepository pagoRepository,Mapper mapper,AccesosUsuariosRepository accesosUsuariosRepository) {
        this.mapper = mapper;
        this.pagoRepository = pagoRepository;
        this.accesosUsuariosRepository = accesosUsuariosRepository;
    }

    @Override
    public Pago obtenerPagoPorId(UUID idPago) {

        Optional<Pago> pago =pagoRepository.findById(idPago);
        if(pago.isEmpty()){
            throw new RuntimeException("Error: No existe ningún pago asociado a ese id");
        }
        return pago.get();
    }

    @Override
    public List<Pago> obtenerPagoes() {
       return pagoRepository.findAll();
    }

    @Override
    public Pago guardarPago(PagoCreacionDTO Pago) {
        Pago pago = pagoRepository.save(mapper.pagoCreacionDTOtoPago(Pago));
        AccesosUsuarios accesosUsuarios = accesosUsuariosRepository.findByUsuarioAndAcceso(pago.getUsuario(), pago.getAcceso());
        accesosUsuarios.setActive(true);
        accesosUsuariosRepository.save(accesosUsuarios);
        return pago;

    }

    @Override
    public Pago editarPago(PagoCreacionDTO Pago, UUID idPago) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editarPago'");
    }

    @Override
    public void eliminarPago(UUID idPago) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarPago'");
    }
    
}
