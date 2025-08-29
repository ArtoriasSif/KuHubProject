package Usuario.Msvc_Usuario.exceptions;

import lombok.Getter;

@Getter
public class SeccionNotFoundException extends RuntimeException {

    private final Long idSeccion;

    public SeccionNotFoundException(Long idSeccion) {
        super("No existe una sección con id: " + idSeccion);
        this.idSeccion = idSeccion;
    }
}