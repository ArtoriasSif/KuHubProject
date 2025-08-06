package msvc_DetalleProductoSolicitud.DetalleProducto.services;

import msvc_DetalleProductoSolicitud.DetalleProducto.models.entity.DetalleProductoSolicitud;

import java.util.List;

public interface DetalleProductoSolicitudService {
    DetalleProductoSolicitud saveDetalleProductoSolicitud(DetalleProductoSolicitud detalleProductoSolicitud);
    DetalleProductoSolicitud findById(Long id);
    List<DetalleProductoSolicitud> findAll();
}
