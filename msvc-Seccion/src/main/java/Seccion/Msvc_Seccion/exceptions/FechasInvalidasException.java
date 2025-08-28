package Seccion.Msvc_Seccion.exceptions;

public class FechasInvalidasException extends RuntimeException {
    public FechasInvalidasException() {
        super("La lista de fechas no puede ser nula ni vacía para agregar");
    }
}
