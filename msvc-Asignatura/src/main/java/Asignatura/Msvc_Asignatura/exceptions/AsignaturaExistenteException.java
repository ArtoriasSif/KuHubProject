package Asignatura.Msvc_Asignatura.exceptions;

public class AsignaturaExistenteException extends RuntimeException {
    public AsignaturaExistenteException(String nombre) {
        super("La asignatura con el nombre '" + nombre + "' ya está registrada.");
    }
}
