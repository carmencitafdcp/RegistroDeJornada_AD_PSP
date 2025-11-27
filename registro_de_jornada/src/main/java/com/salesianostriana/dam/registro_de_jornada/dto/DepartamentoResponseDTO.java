package com.salesianostriana.dam.registro_de_jornada.dto;

import java.math.BigDecimal;
import com.salesianostriana.dam.registro_de_jornada.model.Departamento;

public record DepartamentoResponseDTO(
        Long id,
        String nombre,
        BigDecimal presupuesto
) {
    public static DepartamentoResponseDTO fromEntity(Departamento d) {
        if (d == null) return null;
        return new DepartamentoResponseDTO(
                d.getDepartamento_id(),
                d.getNombre(),
                d.getPresupuesto()
        );
    }
}