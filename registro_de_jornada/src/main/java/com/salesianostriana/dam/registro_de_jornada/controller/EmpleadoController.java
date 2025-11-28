package com.salesianostriana.dam.registro_de_jornada.controller;

import com.salesianostriana.dam.registro_de_jornada.dto.EmpleadoRequestDTO;
import com.salesianostriana.dam.registro_de_jornada.dto.EmpleadoResponseDTO;
import com.salesianostriana.dam.registro_de_jornada.model.Empleado;
import com.salesianostriana.dam.registro_de_jornada.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Empleados", description = "Operaciones sobre empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpleadoResponseDTO.class)))
    })
    public ResponseEntity<List<EmpleadoResponseDTO>> listAll() {
        return ResponseEntity.ok(empleadoService.findAll());
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpleadoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EmpleadoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Crear empleado", description = "Crea un nuevo empleado. Si se incluye departamentoId en el body se le asigna tras la creación.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empleado creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpleadoResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "nombreCompleto": "Jorge Fernández",
                                      "cargo": "Frontend",
                                      "salario": 30000.00,
                                      "departamento": {
                                        "id": 1,
                                        "nombre": "Desarrollo de aplicaciones multiplataforma",
                                        "presupuesto": 150000.00
                                      }
                                    }
                                    """)))
    })
    public ResponseEntity<EmpleadoResponseDTO> create(@RequestBody EmpleadoRequestDTO dto) {
        Empleado empleado = Empleado.builder()
                .nombreCompleto(dto.nombreCompleto())
                .cargo(dto.cargo())
                .salario(dto.salario())
                .build();

        EmpleadoResponseDTO saved = empleadoService.create(empleado);

        if (dto.departamentoId() != null) {
            saved = empleadoService.assignDepartment(saved.id(), dto.departamentoId());
        }

        return ResponseEntity.created(URI.create("/api/v1/empleados/" + saved.id())).body(saved);
    }

    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpleadoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "400", description = "Presupuesto excedido (al cambiar salario)")
    })
    public ResponseEntity<EmpleadoResponseDTO> edit(@PathVariable Long id, @RequestBody EmpleadoRequestDTO dto) {
        Empleado cambios = Empleado.builder()
                .nombreCompleto(dto.nombreCompleto())
                .cargo(dto.cargo())
                .salario(dto.salario())
                .build();
        return ResponseEntity.ok(empleadoService.edit(id, cambios));
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empleado eliminado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        empleadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{empleadoId}/departamento/{deptoId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento asignado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpleadoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Empleado o departamento no encontrado")
    })
    public ResponseEntity<EmpleadoResponseDTO> assignDepartment(@PathVariable Long empleadoId, @PathVariable Long deptoId) {
        return ResponseEntity.ok(empleadoService.assignDepartment(empleadoId, deptoId));
    }
}