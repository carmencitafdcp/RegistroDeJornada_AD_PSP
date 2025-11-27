package com.salesianostriana.dam.registro_de_jornada.dto;

import com.salesianostriana.dam.registro_de_jornada.model.Empleado;

import java.math.BigDecimal;

public record EmpleadoRequestDTO(
        String nombreCompleto,
        String cargo,
        BigDecimal salario,
        Long departamentoId
) {
    public Empleado toEntity() {
        return Empleado.builder()
                .nombreCompleto(this.nombreCompleto)
                .cargo(this.cargo)
                .salario(this.salario)
                .build();
    }
}