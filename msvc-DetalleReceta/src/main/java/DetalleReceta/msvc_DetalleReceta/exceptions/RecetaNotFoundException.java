package DetalleReceta.msvc_DetalleReceta.exceptions;

public class RecetaNotFoundException extends DetalleRecetaException {
  public RecetaNotFoundException(Long idReceta) {
    super("La receta con id " + idReceta + " no existe");
  }
}
