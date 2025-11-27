package com.salesianostriana.dam.registro_de_jornada.dto;

import com.salesianostriana.dam.registro_de_jornada.model.Departamento;

import java.math.BigDecimal;

public record DepartamentoRequestDTO(
        String nombre,
        BigDecimal presupuesto
) {
    public Departamento toEntity() {
        return Departamento.builder()
                .nombre(this.nombre)
                .presupuesto(this.presupuesto)
                .build();
    }
}