package DetalleReceta.msvc_DetalleReceta.services;

import DetalleReceta.msvc_DetalleReceta.models.DetalleReceta;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DetalleRecetaService {
    DetalleReceta findByIdReceta(Long id);
    List<DetalleReceta> findAllRecetas();
    DetalleReceta saveDetalleReceta(DetalleReceta detalleReceta);

}
