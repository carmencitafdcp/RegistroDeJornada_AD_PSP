package com.salesianostriana.dam.registro_de_jornada.dto;

import java.math.BigDecimal;
import com.salesianostriana.dam.registro_de_jornada.model.Empleado;

public record EmpleadoResponseDTO(
        Long id,
        String nombreCompleto,
        String cargo,
        BigDecimal salario,
        Long departamentoId,
        String departamentoNombre
) {
    public static EmpleadoResponseDTO fromEntity(Empleado e) {
        if (e == null) return null;
        Long deptId = null;
        String deptNombre = null;
        if (e.getDepartamento() != null) {
            deptId = e.getDepartamento().getId();
            deptNombre = e.getDepartamento().getNombre();
        }
        return new EmpleadoResponseDTO(
                e.getId(),
                e.getNombreCompleto(),
                e.getCargo(),
                e.getSalario(),
                deptId,
                deptNombre
        );
    }
}