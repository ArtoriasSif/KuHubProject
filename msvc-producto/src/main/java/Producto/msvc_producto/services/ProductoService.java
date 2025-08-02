package Producto.msvc_producto.services;

import Producto.msvc_producto.models.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findByNombreProducto(String nombreProducto);
    Producto save (Producto producto);
}
