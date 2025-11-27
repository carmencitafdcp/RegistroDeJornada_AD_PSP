package com.salesianostriana.dam.registro_de_jornada.repository;


import com.salesianostriana.dam.registro_de_jornada.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository <Departamento, Long> {
    Optional<Departamento> findByNombre(String nombre);
}
