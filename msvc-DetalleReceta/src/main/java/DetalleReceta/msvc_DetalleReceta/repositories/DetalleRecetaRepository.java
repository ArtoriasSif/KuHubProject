package DetalleReceta.msvc_DetalleReceta.repositories;

import DetalleReceta.msvc_DetalleReceta.models.entities.DetalleReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleRecetaRepository extends JpaRepository<DetalleReceta, Long> {
    boolean existsByIdReceta(Long idReceta);
    void deleteByIdReceta(Long idReceta);

}
