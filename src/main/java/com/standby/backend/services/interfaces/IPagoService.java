package com.standby.backend.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.standby.backend.DTOs.PagoCreacionDTO;
import com.standby.backend.models.Pago;

public interface IPagoService {
     public Pago obtenerPagoPorId(UUID idPago);
    public List<Pago> obtenerPagoes();

    public Pago guardarPago(PagoCreacionDTO Pago);
    public Pago editarPago(PagoCreacionDTO Pago, UUID idPago);
    public void eliminarPago(UUID idPago);
}
