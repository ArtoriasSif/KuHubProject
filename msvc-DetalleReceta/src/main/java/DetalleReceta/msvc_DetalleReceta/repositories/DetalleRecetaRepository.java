package DetalleReceta.msvc_DetalleReceta.repositories;

import DetalleReceta.msvc_DetalleReceta.models.DetalleReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleRecetaRepository extends JpaRepository<DetalleReceta, Long> {
}
