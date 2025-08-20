package DetalleReceta.msvc_DetalleReceta.services;


import DetalleReceta.msvc_DetalleReceta.clients.ProductoClientRest;
import DetalleReceta.msvc_DetalleReceta.clients.RecetaClientRest;
import DetalleReceta.msvc_DetalleReceta.exceptions.DetalleRecetaException;
import DetalleReceta.msvc_DetalleReceta.models.DetalleReceta;
import DetalleReceta.msvc_DetalleReceta.models.Producto;
import DetalleReceta.msvc_DetalleReceta.models.entities.Receta;
import DetalleReceta.msvc_DetalleReceta.repositories.DetalleRecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DetalleRecetasServiceImpl implements DetalleRecetaService {

    @Autowired
    private DetalleRecetaRepository detalleRecetaRepository;

    @Autowired
    private RecetaClientRest recetaClientRest;

    @Autowired
    private ProductoClientRest productoClientRest;

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

        try {
            Receta receta= recetaClientRest.findByIdReceta(detalleReceta.getIdReceta());
        }catch (Exception e){
            throw new DetalleRecetaException("Receta no encontrada");
        }

        try{
            Producto producto = productoClientRest.findProductoById(detalleReceta.getIdProducto());
        }catch (Exception e){
            throw new DetalleRecetaException("Producto no encontrado");
        }

        if (detalleReceta.getCantidadUnidadMedida() <= 0){
            throw new RuntimeException("La cantidad de unidad medida no puede ser menor a 0");
        }


        return detalleRecetaRepository.save(detalleReceta);
    }

}
