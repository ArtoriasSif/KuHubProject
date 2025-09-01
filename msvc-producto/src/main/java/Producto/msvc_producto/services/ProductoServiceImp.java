package Producto.msvc_producto.services;

import Producto.msvc_producto.clients.DetalleProductoSolicitudClientRest;
import Producto.msvc_producto.clients.DetalleRecetaClientRest;
import Producto.msvc_producto.dtos.ProductoUpdateRequest;
import Producto.msvc_producto.exceptions.ProductoException;
import Producto.msvc_producto.exceptions.ProductoExistenteException;
import Producto.msvc_producto.exceptions.ProductoNotFoundException;
import Producto.msvc_producto.exceptions.ProductoVinculadoException;
import Producto.msvc_producto.models.entity.Categoria;
import Producto.msvc_producto.models.entity.Producto;
import Producto.msvc_producto.repositories.ProductoRepository;
import Producto.msvc_producto.utils.StringUtils;
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

    @Autowired
    private DetalleRecetaClientRest detalleRecetaClientRest;

    @Transactional
    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Producto findByNombreProducto(String nombre){
        return productoRepository.findByNombreProducto(nombre).orElseThrow(
                ()-> new ProductoNotFoundException(nombre)
        );
    }

    @Transactional
    @Override
    public Producto findById(Long id){
        return productoRepository.findById(id).orElseThrow(
                ()-> new ProductoNotFoundException(id)
        );
    }

    @Transactional
    @Override
    public Producto save (Producto producto) {


        String capitalizado = StringUtils.capitalizarPalabras(producto.getNombreProducto());

        //validar que no exista producto con ese nombre
        if (productoRepository.findByNombreProducto(capitalizado).isPresent()){
            throw new ProductoExistenteException(capitalizado);
        }

        producto.setNombreProducto(capitalizado);
        return productoRepository.save(producto);
    }

    @Transactional
    @Override
    public Producto updateByName(String nombreProductoActual , ProductoUpdateRequest productoRequest) {

        String nombrePActual = StringUtils.capitalizarPalabras(nombreProductoActual);
        String nombrePNuevo = StringUtils.capitalizarPalabras(productoRequest.getNombreProductoNuevo());

        Producto P = productoRepository.findByNombreProducto(nombrePActual).orElseThrow(
                ()-> new ProductoNotFoundException(nombrePActual)
        );

        if (!nombrePActual.equals(nombrePNuevo) &&
                productoRepository.existsByNombreProducto(nombrePNuevo)) {
            throw new ProductoExistenteException(nombrePNuevo);
        }

        P.setNombreProducto(nombrePNuevo);
        P.setUnidadMedida(productoRequest.getUnidadMedida());
        return productoRepository.save(P);
    }

    @Transactional
    @Override
    public Producto updateById(Long id, ProductoUpdateRequest productoRequest){

        Producto P = productoRepository.findById(id).orElseThrow(() ->
                new ProductoNotFoundException(id));

        String nombrePNuevo = StringUtils.capitalizarPalabras(productoRequest.getNombreProductoNuevo());

        if (!P.getNombreProducto().equals(nombrePNuevo) &&
                productoRepository.existsByNombreProducto(nombrePNuevo)) {
            throw new ProductoExistenteException(nombrePNuevo);
        }

        P.setNombreProducto(nombrePNuevo);
        P.setUnidadMedida(productoRequest.getUnidadMedida());
        return productoRepository.save(P);
    }

    // Metodo accedido por Client para verificar si existe producto vinculado al detalle
    @Transactional
    @Override
    public void deleteByName(String nombreProducto) {

        String nombreProductoCapitalizado = StringUtils.capitalizarPalabras(nombreProducto);

        Producto producto = productoRepository.findByNombreProducto(nombreProductoCapitalizado).orElseThrow(
                ()-> new ProductoNotFoundException(nombreProductoCapitalizado)
        );

        boolean enDetalleSolicitud = detalleProductoSolicitudClientRest.existeProductoIdEnDetalle(producto.getIdProducto());
        boolean enDetalleReceta = detalleRecetaClientRest.existsByIdProducto(producto.getIdProducto());

        if (!enDetalleSolicitud && !enDetalleReceta) {
            productoRepository.delete(producto);
        } else {
            throw new ProductoVinculadoException(nombreProductoCapitalizado);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id)  {
        Producto producto = productoRepository.findById(id).orElseThrow(
                () -> new ProductoNotFoundException(id));


        boolean enDetalleSolicitud = detalleProductoSolicitudClientRest.existeProductoIdEnDetalle(producto.getIdProducto());
        boolean enDetalleReceta = detalleRecetaClientRest.existsByIdProducto(producto.getIdProducto());

        if (!enDetalleSolicitud && !enDetalleReceta) {
            productoRepository.delete(producto);
        } else {
            throw new ProductoVinculadoException(producto.getNombreProducto());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByIds(List<Long> ids) {
        return productoRepository.findAllById(ids);
    }

    @Override
    public Categoria findCategoriaByIdProducto(Long idProducto) {
        Producto producto = productoRepository.findById(idProducto).orElseThrow(
                () -> new ProductoNotFoundException(idProducto)
        );
        return producto.getCategoria();
    }

    @Override
    public Categoria findCategoriaByNombreProducto(String nombreProducto) {
        Producto producto = productoRepository.findByNombreProducto(nombreProducto).orElseThrow(
                () -> new ProductoNotFoundException(nombreProducto)
        );
        return producto.getCategoria();
    }

    @Override
    public List<Producto> findByCategoriaIdCategoria(Long idCategoria) {
        return this.productoRepository.findByCategoriaIdCategoria(idCategoria);
    }

}
