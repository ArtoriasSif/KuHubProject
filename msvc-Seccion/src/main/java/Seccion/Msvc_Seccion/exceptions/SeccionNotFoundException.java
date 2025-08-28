package Seccion.Msvc_Seccion.exceptions;

public class SeccionNotFoundException extends RuntimeException{
    public SeccionNotFoundException(Long id) {
        super("No se encontró la seccion con el id: " + id);
    }
}
