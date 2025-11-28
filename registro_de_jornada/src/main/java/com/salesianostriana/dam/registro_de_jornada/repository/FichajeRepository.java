package com.salesianostriana.dam.registro_de_jornada.repository;

import com.salesianostriana.dam.registro_de_jornada.model.Fichaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FichajeRepository extends JpaRepository<Fichaje, Long> {
    Optional<Fichaje> findTopByEmpleadoIdOrderByMomentoDesc(Long empleadoId);
    //Esto es para los fichajes que puede que aun no existan
    List<Fichaje> findByEmpleadoIdOrderByMomentoDesc(Long empleadoId);
}