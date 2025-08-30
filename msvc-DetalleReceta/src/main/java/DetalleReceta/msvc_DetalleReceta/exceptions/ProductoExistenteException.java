package DetalleReceta.msvc_DetalleReceta.exceptions;

import lombok.Getter;

@Getter
public class ProductoExistenteException extends RuntimeException {
  public ProductoExistenteException(String message) {
    super(message);
  }
}