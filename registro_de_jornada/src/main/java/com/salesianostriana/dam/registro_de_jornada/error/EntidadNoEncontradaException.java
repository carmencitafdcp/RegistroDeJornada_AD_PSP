package com.salesianostriana.dam.registro_de_jornada.error;

public class EntidadNoEncontradaException extends RuntimeException {
    public EntidadNoEncontradaException() {
        super();
    }

    public EntidadNoEncontradaException(String message) {
        super("Entidad no encontrada");
    }

    public EntidadNoEncontradaException(String entityName, Long id) {
        super(String.format("%s con id %d no encontrado", entityName, id));
    }

    public EntidadNoEncontradaException(Class<?> entityClass, Long id) {
        this(entityClass.getSimpleName(), id);
    }
}
