package com.salesianostriana.dam.registro_de_jornada.error;

public class DepartamentoNotFoundException extends RuntimeException {
  public DepartamentoNotFoundException(String message) {super(message);}
  public DepartamentoNotFoundException(Long id) {super("No se ha encontrado el departamento con id %d".formatted(id));}
  public DepartamentoNotFoundException() {super("Departamento no encontrado");}
}
