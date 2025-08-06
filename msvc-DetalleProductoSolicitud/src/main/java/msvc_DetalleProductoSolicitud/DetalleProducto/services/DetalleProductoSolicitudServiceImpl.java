package msvc_DetalleProductoSolicitud.DetalleProducto.services;

import feign.FeignException;
import msvc_DetalleProductoSolicitud.DetalleProducto.clients.ProductoClientRest;
import msvc_DetalleProductoSolicitud.DetalleProducto.exceptions.DetalleProductoSolicitudException;
import msvc_DetalleProductoSolicitud.DetalleProducto.models.Producto;
import msvc_DetalleProductoSolicitud.DetalleProducto.models.entity.DetalleProductoSolicitud;
import msvc_DetalleProductoSolicitud.DetalleProducto.repositories.DetalleProductoSolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DetalleProductoSolicitudServiceImpl implements DetalleProductoSolicitudService {

    @Autowired
    private DetalleProductoSolicitudRepository detalleProductoSolicitudRepository;

    @Autowired
    private ProductoClientRest productoClientRest;

    @Transactional
    @Override
    public DetalleProductoSolicitud findById(Long id) {
        return detalleProductoSolicitudRepository.findById(id).orElseThrow(
                ()-> new DetalleProductoSolicitudException("Detalle de producto solicitud con el id "+id+" no encontrado")
        );
    }

    @Transactional
    @Override
    public List<DetalleProductoSolicitud> findAllDetalleProductoSolicitud() {
        return detalleProductoSolicitudRepository.findAll();
    }

    @Transactional
    @Override
    public DetalleProductoSolicitud saveDetalleProductoSolicitud(DetalleProductoSolicitud detalleProductoSolicitud) {

        //validar que existe producto y solicitud por client, si no existe lanzar excepcion.
        try {
            Producto producto = productoClientRest.findProductoById(detalleProductoSolicitud.getIdProducto());
        } catch (FeignException.NotFound ex) {
            throw new DetalleProductoSolicitudException("No existe producto con el id: " + detalleProductoSolicitud.getIdProducto());
        }

        //validar que cantidad sea mayor que cero
        if (detalleProductoSolicitud.getCantidadUnidadMedida() <= 0) {
            throw new DetalleProductoSolicitudException("La cantidad debe ser mayor que cero");
        }

        return detalleProductoSolicitudRepository.save(detalleProductoSolicitud);
    }

}
