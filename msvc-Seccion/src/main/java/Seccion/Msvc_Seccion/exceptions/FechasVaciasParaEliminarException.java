package Seccion.Msvc_Seccion.exceptions;

public class FechasVaciasParaEliminarException extends RuntimeException {
    public FechasVaciasParaEliminarException() {
        super("La lista de fechas no puede ser nula ni vacía para eliminar");
    }
}