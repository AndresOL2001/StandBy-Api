package com.standby.backend.models;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pagos",schema="standby")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Pago {

    @Id
    private UUID pago_id;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idAcceso")
    private Acceso acceso;

    private BigDecimal payment_amount;

    private LocalDateTime payment_date;
}
