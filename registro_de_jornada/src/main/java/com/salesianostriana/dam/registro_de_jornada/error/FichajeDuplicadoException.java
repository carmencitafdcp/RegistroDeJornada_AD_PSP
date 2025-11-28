package com.salesianostriana.dam.registro_de_jornada.error;

public class FichajeDuplicadoException extends RuntimeException {
    public FichajeDuplicadoException() {
        super();
    }

    public FichajeDuplicadoException(String message) {
        super(message);
    }

    public FichajeDuplicadoException(Long empleadoId, String detalle) {
        super(String.format("No se puede registrar el fichaje para el empleado %d: %s", empleadoId, detalle));
    }
}
