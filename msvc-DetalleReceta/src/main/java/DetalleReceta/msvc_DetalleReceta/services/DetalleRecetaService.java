package DetalleReceta.msvc_DetalleReceta.services;

import DetalleReceta.msvc_DetalleReceta.dtos.DetalleRecetaIUpdateQuantityRequestDTO;
import DetalleReceta.msvc_DetalleReceta.models.entities.DetalleReceta;

import java.util.List;

public interface DetalleRecetaService {
    DetalleReceta findByIdReceta(Long id);
    List<DetalleReceta> findAllRecetas();
    DetalleReceta saveDetalleReceta(DetalleReceta detalleReceta);
    DetalleReceta detalleRecetaUpdateQuantity
            (Long id, DetalleRecetaIUpdateQuantityRequestDTO quantityRequest);
    void deletarTodoByIdReceta (Long idReceta);
    void deleteByidDetalleReceta(Long idDetalleReceta);
    boolean existsByIdReceta(Long idReceta);
}
