package DetalleReceta.msvc_DetalleReceta.exceptions;

import lombok.Getter;

@Getter
public class DetalleRecetaNotFoundException extends DetalleRecetaException {



    public DetalleRecetaNotFoundException(Long id) {
        super("Detalle Receta con id: " + id + " no encontrado");
    }

    public DetalleRecetaNotFoundException(String mensaje) {
        super(mensaje);
    }
}