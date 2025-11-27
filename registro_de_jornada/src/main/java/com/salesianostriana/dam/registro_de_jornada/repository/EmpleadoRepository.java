package com.salesianostriana.dam.registro_de_jornada.repository;

import com.salesianostriana.dam.registro_de_jornada.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByDepartamentoId(Long departamentoId);
}
