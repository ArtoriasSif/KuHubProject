package msvc_DetalleProductoSolicitud.DetalleProducto.services;

import msvc_DetalleProductoSolicitud.DetalleProducto.dtos.DetalleProductoSolicitudUpdateQuantityRequestDTO;
import msvc_DetalleProductoSolicitud.DetalleProducto.models.entity.DetalleProductoSolicitud;

import java.util.List;

public interface DetalleProductoSolicitudService {
    DetalleProductoSolicitud saveDetalleProductoSolicitud(DetalleProductoSolicitud detalleProductoSolicitud);
    DetalleProductoSolicitud findById(Long id);
    List<DetalleProductoSolicitud> findAll();
    boolean existeProductoEnDetalle (String nombreProducto);
    boolean existeProductoIdEnDetalle (Long idProducto);
    DetalleProductoSolicitud detalleProductoSolicitudUpdateQuantity
            (Long id, DetalleProductoSolicitudUpdateQuantityRequestDTO quantityRequest);
    void deleteByIdDetalleP(Long id);
    void deleteAllDetalleProductoSolicitud(Long idSolicitudDocente);
}
