package com.salesianostriana.dam.registro_de_jornada.service;

import com.salesianostriana.dam.registro_de_jornada.error.DepartamentoNotFoundException;
import com.salesianostriana.dam.registro_de_jornada.model.Departamento;
import com.salesianostriana.dam.registro_de_jornada.model.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoService {
    private final DepartamentoRepository departamentoRepository;

    public List<Departamento> getAll(){
        List<Departamento> result = departamentoRepository.findAll();
        if (result.isEmpty())
            throw new DepartamentoNotFoundException("No hay departamentos registrados");
        return result;
    }

    public Departamento getById(Long id){
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new DepartamentoNotFoundException(id));
    }

//    public void delete(Departamento departamento){
//        departamentoRepository.deleteById(departamento.getdepartamento_id());
//    }

    public void deleteById(Long id){
        departamentoRepository.deleteById(id);
    }


}
