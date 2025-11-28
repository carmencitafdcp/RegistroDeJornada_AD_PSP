package com.salesianostriana.dam.registro_de_jornada.controller;

import com.salesianostriana.dam.registro_de_jornada.dto.DepartamentoRequestDTO;
import com.salesianostriana.dam.registro_de_jornada.dto.DepartamentoResponseDTO;
import com.salesianostriana.dam.registro_de_jornada.model.Departamento;
import com.salesianostriana.dam.registro_de_jornada.service.DepartamentoService;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/departamentos")
@RequiredArgsConstructor
@Tag(name = "Departamentos", description = "Operaciones sobre departamentos")
public class DepartamentoController {
    private final DepartamentoService departamentoService;

    @GetMapping
    @Operation(summary = "Obtener todos los departamentos", description = "Devuelve la lista de departamentos con id, nombre y presupuesto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de departamentos encontrada",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DepartamentoResponseDTO.class)),
                            examples = {
                                    @ExampleObject(value = """
                                            [
                                              {
                                                "id": 1,
                                                "nombre": "Desarrollo de aplicaciones multiplataforma",
                                                "presupuesto": 150000.00
                                              }
                                            ]
                                            """)
                            }
                    )
            )
    })

    public ResponseEntity<List<DepartamentoResponseDTO>> listAll() {
        return ResponseEntity.ok(departamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departamentoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<DepartamentoResponseDTO> create(@RequestBody DepartamentoRequestDTO dto) {
        Departamento departamento = Departamento.builder()
                .nombre(dto.nombre())
                .presupuesto(dto.presupuesto())
                .build();
        DepartamentoResponseDTO saved = departamentoService.create(departamento);
        return ResponseEntity.created(URI.create("/api/v1/departamentos/" + saved.id())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> edit(@PathVariable Long id, @RequestBody DepartamentoRequestDTO dto) {
        Departamento cambios = Departamento.builder()
                .nombre(dto.nombre())
                .presupuesto(dto.presupuesto())
                .build();
        return ResponseEntity.ok(departamentoService.edit(id, cambios));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
