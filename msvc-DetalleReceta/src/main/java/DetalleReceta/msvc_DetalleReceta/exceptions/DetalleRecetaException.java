package DetalleReceta.msvc_DetalleReceta.exceptions;

public class DetalleRecetaException extends RuntimeException {

    public DetalleRecetaException(String message) {
        super(message);
    }

    public DetalleRecetaException(Long id) {
        super("Detalle de receta con id " + id + " no encontrado");
    }

    public DetalleRecetaException(String message, Throwable cause) {
        super(message, cause);
    }
}
