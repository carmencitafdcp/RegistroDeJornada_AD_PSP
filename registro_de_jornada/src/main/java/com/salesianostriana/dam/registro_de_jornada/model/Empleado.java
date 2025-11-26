package com.salesianostriana.dam.registro_de_jornada.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Empleados")
public class Empleado {
    @Id
    @GeneratedValue

    private Long id;
    private String nombreCompleto;
    private String cargo;
    private BigDecimal salario;

    //@OneToMany(mappedBy = "Departamento", cascade = )
}
