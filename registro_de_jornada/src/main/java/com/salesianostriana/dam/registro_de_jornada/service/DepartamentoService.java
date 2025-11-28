package com.salesianostriana.dam.registro_de_jornada.service;

import com.salesianostriana.dam.registro_de_jornada.dto.DepartamentoResponseDTO;
import com.salesianostriana.dam.registro_de_jornada.error.EntidadNoEncontradaException;
import com.salesianostriana.dam.registro_de_jornada.error.PresupuestoExcedidoException;
import com.salesianostriana.dam.registro_de_jornada.model.Departamento;
import com.salesianostriana.dam.registro_de_jornada.model.Empleado;
import com.salesianostriana.dam.registro_de_jornada.repository.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartamentoService {
    private final DepartamentoRepository departamentoRepository;

    public List<DepartamentoResponseDTO> findAll() {
        return departamentoRepository.findAll().stream().map(DepartamentoResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public DepartamentoResponseDTO getById(Long id) {
        Departamento departamento = findById(id);
        return DepartamentoResponseDTO.fromEntity(departamento);
    }

    public DepartamentoResponseDTO create(Departamento departamento) {
        Departamento saved = departamentoRepository.save(departamento);
        return DepartamentoResponseDTO.fromEntity(saved);
    }

    public DepartamentoResponseDTO edit(Long id, Departamento cambios) {
        Departamento departamento = findById(id);
        departamento.setNombre(cambios.getNombre());
        departamento.setPresupuesto(cambios.getPresupuesto());
        Departamento saved = departamentoRepository.save(departamento);
        return DepartamentoResponseDTO.fromEntity(saved);
    }

    public void deleteById(Long id) {
        if (!departamentoRepository.existsById(id)) {
            throw new EntidadNoEncontradaException(Departamento.class, id);
        }
        departamentoRepository.deleteById(id);
    }

    public Departamento findById(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException(Departamento.class, id));
    }

    public void salaryChange(Long departamentoId, BigDecimal nuevoSalario, Long empleadoId) {
        Departamento departamento = findById(departamentoId);
        BigDecimal suma = departamento.getEmpleados() == null ? BigDecimal.ZERO
                : departamento.getEmpleados().stream()
                .filter(empleado -> !empleado.getId().equals(empleadoId))
                .map(Empleado::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sumaConNuevo = suma.add(nuevoSalario);
        if (sumaConNuevo.compareTo(departamento.getPresupuesto()) > 0) {
            BigDecimal exceso = sumaConNuevo.subtract(departamento.getPresupuesto());
            throw new PresupuestoExcedidoException(departamentoId, exceso);
        }
    }
}