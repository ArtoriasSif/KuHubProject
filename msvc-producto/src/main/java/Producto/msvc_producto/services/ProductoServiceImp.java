package Producto.msvc_producto.services;

import Producto.msvc_producto.exceptions.ProductoException;
import Producto.msvc_producto.models.Producto;
import Producto.msvc_producto.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImp implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Producto findByNombreProducto(String nombre){
        return productoRepository.findByNombreProducto(nombre).orElseThrow(
                ()-> new ProductoException("Producto con el nombre "+nombre+" no encontrado")
        );
    }


    @Transactional
    @Override
    public Producto save (Producto producto) {

        String nombre = producto.getNombreProducto().trim();
        String capitalizado = Arrays.stream(nombre.split("\\s+"))
                .map(p -> p.substring(0, 1).toUpperCase() + p.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

        //validar que no exista producto con ese nombre
        if (productoRepository.findByNombreProducto(capitalizado).isPresent()){
            throw new ProductoException("Ya existe un producto con el nombre: " + capitalizado);
        }

        producto.setNombreProducto(capitalizado);
        return productoRepository.save(producto);

    }

}
