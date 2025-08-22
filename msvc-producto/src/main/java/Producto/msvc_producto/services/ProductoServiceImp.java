package Producto.msvc_producto.services;

import Producto.msvc_producto.clients.DetalleProductoSolicitudClientRest;
import Producto.msvc_producto.dtos.ProductoUpdateRequest;
import Producto.msvc_producto.exceptions.ProductoException;
import Producto.msvc_producto.models.entity.Producto;
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

    @Autowired
    private DetalleProductoSolicitudClientRest detalleProductoSolicitudClientRest;

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
    public Producto findById(Long id){
        return productoRepository.findById(id).orElseThrow(
                ()-> new ProductoException("Producto con el id "+id+" no encontrado")
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
            throw new ProductoException("Producto existente: " + capitalizado);
        }

        producto.setNombreProducto(capitalizado);
        return productoRepository.save(producto);
    }

    @Transactional
    @Override
    public Producto updateByName(String nombreProductoActual , ProductoUpdateRequest productoRequest) {

        Producto P = productoRepository.findByNombreProducto(nombreProductoActual).orElseThrow(
                ()-> new ProductoException("Producto con el nombre "+nombreProductoActual+" no encontrado")
        );

        if (!nombreProductoActual.equals(productoRequest.getNombreProductoNuevo()) &&
                productoRepository.existsByNombreProducto(productoRequest.getNombreProductoNuevo())) {
            throw new ProductoException("Ya existe un producto con el nombre: " + productoRequest.getNombreProductoNuevo());
        }

        P.setNombreProducto(productoRequest.getNombreProductoNuevo());
        P.setUnidadMedida(productoRequest.getUnidadMedida());
        return productoRepository.save(P);
    }

    @Transactional
    @Override
    public Producto updateById(Long id, ProductoUpdateRequest productoRequest){

        Producto P = productoRepository.findById(id).orElseThrow(() ->
                new ProductoException("Producto con el id "+id+" no encontrado"));

        if (!P.getNombreProducto().equals(productoRequest.getNombreProductoNuevo()) &&
                productoRepository.existsByNombreProducto(productoRequest.getNombreProductoNuevo())) {
            throw new ProductoException("Ya existe un producto con el nombre: " + productoRequest.getNombreProductoNuevo());
        }

        P.setNombreProducto(productoRequest.getNombreProductoNuevo());
        P.setUnidadMedida(productoRequest.getUnidadMedida());
        return productoRepository.save(P);
    }

    // Metodo accedido por Client para verificar si existe producto vinculado al detalle
    @Transactional
    @Override
    public void deleteByName(String nombreProducto) throws ProductoException {
        Producto producto = findByNombreProducto(nombreProducto);
        if (producto == null) {
            throw new ProductoException("Producto no encontrado: " + nombreProducto);
        }

        Boolean tieneSolicitud;
        try {
            tieneSolicitud = detalleProductoSolicitudClientRest.existeProductoEnDetalle(nombreProducto);
        } catch (Exception e) {
            throw new ProductoException("Error al verificar solicitudes asociadas al producto: " + e.getMessage());
        }

        if (Boolean.TRUE.equals(tieneSolicitud)) {
            throw new ProductoException("No se puede eliminar un producto con solicitud de docente asociada");
        }

        productoRepository.delete(producto);
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws ProductoException {
        if (!productoRepository.existsById(id)) {
            throw new ProductoException("Producto con ID " + id + " no existe");
        }

        Boolean tieneSolicitud;
        try {
            tieneSolicitud = detalleProductoSolicitudClientRest.existeProductoIdEnDetalle(id);
        } catch (Exception e) {
            throw new ProductoException("Error al verificar solicitudes asociadas al producto con ID " + id);
        }

        if (Boolean.TRUE.equals(tieneSolicitud)) {
            throw new ProductoException("No se puede eliminar un producto con solicitud de docente asociada");
        }

        productoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByIds(List<Long> ids) {
        return productoRepository.findAllById(ids);
    }
}
