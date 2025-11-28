package com.salesianostriana.dam.registro_de_jornada.service;

import com.salesianostriana.dam.registro_de_jornada.dto.FichajeResponseDTO;
import com.salesianostriana.dam.registro_de_jornada.model.Fichaje;
import com.salesianostriana.dam.registro_de_jornada.repository.FichajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FichajeService {

    private final FichajeRepository fichajeRepository;

    public List<FichajeResponseDTO> listByEmpleado(Long empleadoId) {
        return fichajeRepository.findByEmpleadoIdOrderByMomentoDesc(empleadoId).stream().map(FichajeResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public Fichaje save(Fichaje fichaje) {
        return fichajeRepository.save(fichaje);
    }

    public java.util.Optional<Fichaje> findUltimoFichaje(Long empleadoId) {
        return fichajeRepository.findTopByEmpleadoIdOrderByMomentoDesc(empleadoId);
    }
}