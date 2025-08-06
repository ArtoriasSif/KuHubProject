package Producto.msvc_producto.services;

import Producto.msvc_producto.models.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findByNombreProducto(String nombreProducto); //ultilizado en service no en controller
    Producto save (Producto producto);
    Producto findById(Long id);
}
