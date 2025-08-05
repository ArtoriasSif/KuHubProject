package msvc_DetalleProductoSolicitud.DetalleProducto.repositories;

import msvc_DetalleProductoSolicitud.DetalleProducto.models.DetalleProductoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleProductoSolicitudRepository extends JpaRepository<DetalleProductoSolicitud, Long> {
}
