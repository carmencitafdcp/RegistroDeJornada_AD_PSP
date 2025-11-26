package com.salesianostriana.dam.registro_de_jornada.model;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

}
