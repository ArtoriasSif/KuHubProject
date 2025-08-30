package DetalleReceta.msvc_DetalleReceta.exceptions;

public class ProductoClientException extends DetalleRecetaException {
  public ProductoClientException(Long id, Throwable cause) {
    super("Error al consultar producto con id " + id, cause);
  }

  public ProductoClientException(String message) {
    super(message);
  }
}