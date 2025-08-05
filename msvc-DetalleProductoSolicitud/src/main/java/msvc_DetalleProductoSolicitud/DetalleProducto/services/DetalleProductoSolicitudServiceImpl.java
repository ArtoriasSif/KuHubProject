package msvc_DetalleProductoSolicitud.DetalleProducto.services;

import msvc_DetalleProductoSolicitud.DetalleProducto.models.DetalleProductoSolicitud;
import msvc_DetalleProductoSolicitud.DetalleProducto.repositories.DetalleProductoSolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetalleProductoSolicitudServiceImpl implements DetalleProductoSolicitudService {

    @Autowired
    private DetalleProductoSolicitudRepository detalleProductoSolicitudRepository;

    @Transactional
    @Override
    public DetalleProductoSolicitud save(DetalleProductoSolicitud detalleProductoSolicitud) {

        //validar que existe producto y solicitud por client, si no existe lanzar excepcion.


    }

}
