package DetalleReceta.msvc_DetalleReceta.repositories;

import DetalleReceta.msvc_DetalleReceta.models.entities.DetalleReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleRecetaRepository extends JpaRepository<DetalleReceta, Long> {
    boolean existsByIdReceta(Long idReceta);
    void deleteByIdReceta(Long idReceta);
    List<DetalleReceta> findAllByIdReceta(Long idReceta);
    boolean existsByIdProducto(Long idProducto);
    List<DetalleReceta> saveAll(List<DetalleReceta> detalleRecetas);
}
