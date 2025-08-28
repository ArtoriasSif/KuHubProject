package Seccion.Msvc_Seccion.exceptions;

public class SeccionExistenteException extends RuntimeException {
    public SeccionExistenteException(String nombreSeccion) {
        super("La sección con el nombre '" + nombreSeccion + "' ya está registrada en esta asignatura.");
    }
}