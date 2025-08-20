package DetalleReceta.msvc_DetalleReceta.services;


import DetalleReceta.msvc_DetalleReceta.models.DetalleReceta;
import DetalleReceta.msvc_DetalleReceta.repositories.DetalleRecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DetalleRecetasServiceImpl implements DetalleRecetaService {

    @Autowired
    private DetalleRecetaRepository detalleRecetaRepository;

    @Transactional
    @Override
    public DetalleReceta findByIdReceta(Long id){
        return detalleRecetaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Detalle Receta con id: "+id+" no encontrado")
        );
    }

    @Transactional
    @Override
    public List<DetalleReceta> findAllRecetas() {
        return detalleRecetaRepository.findAll();
    }

    @Transactional
    @Override
    public DetalleReceta saveDetalleReceta(DetalleReceta detalleReceta) {
        return detalleRecetaRepository.save(detalleReceta);
    }

}
