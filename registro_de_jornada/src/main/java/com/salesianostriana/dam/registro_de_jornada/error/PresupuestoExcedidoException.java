package com.salesianostriana.dam.registro_de_jornada.error;

import java.math.BigDecimal;

public class PresupuestoExcedidoException extends RuntimeException {
    private BigDecimal exceso;

    public PresupuestoExcedidoException() {
        super();
    }

    public PresupuestoExcedidoException(String message) {
        super(message);
    }

    public PresupuestoExcedidoException(Long departamentoId, BigDecimal exceso) {
        super(String.format("El departamento %d excede el presupuesto en %s", departamentoId, exceso));
        this.exceso = exceso;
    }

    public BigDecimal getExceso() {
        return exceso;
    }
}
