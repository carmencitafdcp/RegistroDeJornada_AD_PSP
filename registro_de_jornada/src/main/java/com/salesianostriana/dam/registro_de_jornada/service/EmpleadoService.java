package com.salesianostriana.dam.registro_de_jornada.service;

import com.salesianostriana.dam.registro_de_jornada.dto.EmpleadoResponseDTO;
import com.salesianostriana.dam.registro_de_jornada.dto.FichajeResponseDTO;
import com.salesianostriana.dam.registro_de_jornada.error.EntidadNoEncontradaException;
import com.salesianostriana.dam.registro_de_jornada.error.FichajeDuplicadoException;
import com.salesianostriana.dam.registro_de_jornada.error.PresupuestoExcedidoException;
import com.salesianostriana.dam.registro_de_jornada.model.Departamento;
import com.salesianostriana.dam.registro_de_jornada.model.Empleado;
import com.salesianostriana.dam.registro_de_jornada.model.Fichaje;
import com.salesianostriana.dam.registro_de_jornada.model.TipoFichaje;
import com.salesianostriana.dam.registro_de_jornada.repository.EmpleadoRepository;
import com.salesianostriana.dam.registro_de_jornada.repository.FichajeRepository;
import com.salesianostriana.dam.registro_de_jornada.repository.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoRepository departamentoRepository;
    private final FichajeRepository fichajeRepository;
    private final FichajeService fichajeService;

    public List<EmpleadoResponseDTO> findAll() {
        return empleadoRepository.findAll().stream().map(EmpleadoResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public EmpleadoResponseDTO getById(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException(Empleado.class, id));
        return EmpleadoResponseDTO.fromEntity(empleado);
    }

    public EmpleadoResponseDTO create(Empleado empleado) {
        Empleado saved = empleadoRepository.save(empleado);
        return EmpleadoResponseDTO.fromEntity(saved);
    }

    public EmpleadoResponseDTO edit(Long id, Empleado cambios) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException(Empleado.class, id));
        empleado.setNombreCompleto(cambios.getNombreCompleto());
        empleado.setCargo(cambios.getCargo());
        empleado.setSalario(cambios.getSalario());
        Empleado saved = empleadoRepository.save(empleado);
        return EmpleadoResponseDTO.fromEntity(saved);
    }

    public void deleteById(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new EntidadNoEncontradaException(Empleado.class, id);
        }
        empleadoRepository.deleteById(id);
    }

    public EmpleadoResponseDTO assignDepartment(Long empleadoId, Long departamentoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new EntidadNoEncontradaException(Empleado.class, empleadoId));
        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new EntidadNoEncontradaException(Departamento.class, departamentoId));
        empleado.setDepartamento(departamento);
        Empleado saved = empleadoRepository.save(empleado);
        return EmpleadoResponseDTO.fromEntity(saved);
    }

    public FichajeResponseDTO fichar(Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new EntidadNoEncontradaException(Empleado.class, empleadoId));

        Optional<Fichaje> ultimoFichajeEmple = fichajeRepository.findTopByEmpleadoIdOrderByMomentoDesc(empleadoId);

        TipoFichaje nuevoTipo = ultimoFichajeEmple.map(u -> u.getTipo() == TipoFichaje.ENTRADA ? TipoFichaje.SALIDA : TipoFichaje.ENTRADA)
                .orElse(TipoFichaje.ENTRADA);

        Optional<Fichaje> ultimoFichajeActual = fichajeRepository.findTopByEmpleadoIdOrderByMomentoDesc(empleadoId);
        if (ultimoFichajeActual.isPresent() && ultimoFichajeActual.get().getTipo() == nuevoTipo) {
            throw new FichajeDuplicadoException(empleadoId, "El último fichaje ya es del mismo tipo");
        }
        Fichaje fichaje = Fichaje.builder().momento(LocalDateTime.now())
                .tipo(nuevoTipo)
                .empleado(empleado)
                .build();
        Fichaje saved = fichajeService.save(fichaje);
        return FichajeResponseDTO.fromEntity(saved);
    }

    public List<FichajeResponseDTO> getFichajes(Long empleadoId) {
        if (!empleadoRepository.existsById(empleadoId)) {
            throw new EntidadNoEncontradaException(Empleado.class, empleadoId);
        }
        return fichajeRepository.findByEmpleadoIdOrderByMomentoDesc(empleadoId)
                .stream()
                .map(FichajeResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public EmpleadoResponseDTO updateSalary(Long empleadoId, BigDecimal nuevoSalario) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new EntidadNoEncontradaException(Empleado.class, empleadoId));

        Departamento departamento = empleado.getDepartamento();
        if (departamento != null) {
            BigDecimal suma = departamento.getEmpleados().stream()
                    .filter(emp -> !emp.equals(empleado))
                    .map(Empleado::getSalario)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal sumaConNuevo = suma.add(nuevoSalario);
            if (sumaConNuevo.compareTo(departamento.getPresupuesto()) > 0) {
                BigDecimal exceso = sumaConNuevo.subtract(departamento.getPresupuesto());
                throw new PresupuestoExcedidoException(departamento.getId(), exceso);
            }
        }

        empleado.setSalario(nuevoSalario);
        Empleado saved = empleadoRepository.save(empleado);
        return EmpleadoResponseDTO.fromEntity(saved);
    }
}