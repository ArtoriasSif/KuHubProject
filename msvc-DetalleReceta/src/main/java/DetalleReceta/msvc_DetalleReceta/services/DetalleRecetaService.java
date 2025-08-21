package DetalleReceta.msvc_DetalleReceta.services;

import DetalleReceta.msvc_DetalleReceta.dtos.DetalleRecetaIUpdateQuantityRequestDTO;
import DetalleReceta.msvc_DetalleReceta.dtos.DetalleRecetaResponseDTO;
import DetalleReceta.msvc_DetalleReceta.models.entities.DetalleReceta;

import java.util.List;

public interface DetalleRecetaService {
    DetalleReceta findByIdReceta(Long id);
    List<DetalleReceta> findAllRecetas();
    List<DetalleRecetaResponseDTO> findAllRecetasConDetalles ();
    DetalleReceta saveDetalleReceta(DetalleReceta detalleReceta);
    DetalleReceta detalleRecetaUpdateQuantity
            (Long id, DetalleRecetaIUpdateQuantityRequestDTO quantityRequest);
    void deletarTodoByIdReceta (Long idReceta);
    void deleteByidDetalleReceta(Long idDetalleReceta);
    boolean existsByIdReceta(Long idReceta);
}
