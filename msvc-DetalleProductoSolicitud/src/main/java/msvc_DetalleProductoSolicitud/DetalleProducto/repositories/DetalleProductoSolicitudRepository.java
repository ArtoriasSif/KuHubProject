package msvc_DetalleProductoSolicitud.DetalleProducto.repositories;

import msvc_DetalleProductoSolicitud.DetalleProducto.models.entity.DetalleProductoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DetalleProductoSolicitudRepository extends JpaRepository<DetalleProductoSolicitud, Long> {
    boolean existsByIdProducto(Long idProducto);

    @Transactional
    void deleteByIdSolicitudDocente(Long idSolicitudDocente);
}
