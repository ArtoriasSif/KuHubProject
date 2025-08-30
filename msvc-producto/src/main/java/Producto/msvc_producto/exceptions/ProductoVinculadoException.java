package Producto.msvc_producto.exceptions;

import lombok.Getter;

@Getter
public class ProductoVinculadoException extends RuntimeException {

    public ProductoVinculadoException(String nombreProducto) {
        super("No se puede eliminar el producto '" + nombreProducto + "' porque está vinculado a otros registros");
    }
}