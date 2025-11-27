package com.salesianostriana.dam.registro_de_jornada.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Fichajes")
@Builder
public class Fichaje {
    @Id
    @GeneratedValue

    private long fichaje_id;
    private LocalDateTime momento;
    private TipoFichaje tipo;
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    enum TipoFichaje {
        ENTRADA,
        SALIDA
    }
}
