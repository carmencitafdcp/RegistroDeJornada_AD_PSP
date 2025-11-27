package com.salesianostriana.dam.registro_de_jornada.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Departamentos")
@Builder
public class Departamento {
    @Id
    @GeneratedValue

    private Long departamento_id;
    private String nombre;
    private BigDecimal presupuesto;
}
