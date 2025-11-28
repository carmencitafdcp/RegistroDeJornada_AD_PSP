package com.salesianostriana.dam.registro_de_jornada.controller;

import com.salesianostriana.dam.registro_de_jornada.dto.FichajeResponseDTO;
import com.salesianostriana.dam.registro_de_jornada.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
@Tag(name = "Fichajes", description = "Operaciones sobre los fichajes de cada empleado")
public class FichajeController {

    private final EmpleadoService empleadoService;

    @PostMapping("/{empleadoId}/fichar")
    @Operation(summary = "Fichar empleado", description = "Registra ENTRADA o SALIDA alternando con el último fichaje")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Fichaje creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FichajeResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "momento": "2025-11-28T09:00:00",
                                      "tipo": "ENTRADA",
                                      "empleadoId": 1
                                    }
                                    """)))
    })
    public ResponseEntity<FichajeResponseDTO> fichar(@PathVariable Long empleadoId) {
        FichajeResponseDTO dto = empleadoService.fichar(empleadoId);
        return ResponseEntity.created(URI.create("/api/v1/empleados/" + empleadoId + "/fichajes")).body(dto);
    }

    @GetMapping("/{empleadoId}/fichajes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial encontrado",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FichajeResponseDTO.class))))
    })
    public ResponseEntity<List<FichajeResponseDTO>> historial(@PathVariable Long empleadoId) {
        return ResponseEntity.ok(empleadoService.getFichajes(empleadoId));
    }
}