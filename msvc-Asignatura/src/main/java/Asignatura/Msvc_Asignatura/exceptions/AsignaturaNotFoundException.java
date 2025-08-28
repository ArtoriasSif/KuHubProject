package Asignatura.Msvc_Asignatura.exceptions;

public class AsignaturaNotFoundException extends RuntimeException {
    public AsignaturaNotFoundException(Long id) {
        super("No se encontró la asignatura con el id: " + id);
    }
    public AsignaturaNotFoundException(String nombre) {
        super("No se encontró la asignatura con nombre: " + nombre);
    }
}
