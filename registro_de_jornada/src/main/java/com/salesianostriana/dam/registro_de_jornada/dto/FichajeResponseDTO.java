package com.salesianostriana.dam.registro_de_jornada.dto;

import com.salesianostriana.dam.registro_de_jornada.model.Fichaje;
import com.salesianostriana.dam.registro_de_jornada.model.TipoFichaje;
import java.time.LocalDateTime;

public record FichajeResponseDTO(
        Long id,
        LocalDateTime momento,
        TipoFichaje tipo
) {
    public static FichajeResponseDTO fromEntity(Fichaje f) {
        if (f == null) return null;
        return new FichajeResponseDTO(
                f.getFichaje_id(),
                f.getMomento(),
                f.getTipo()
        );
    }
}