package DetalleReceta.msvc_DetalleReceta.services;


import DetalleReceta.msvc_DetalleReceta.clients.ProductoClientRest;
import DetalleReceta.msvc_DetalleReceta.clients.RecetaClientRest;
import DetalleReceta.msvc_DetalleReceta.dtos.DetalleRecetaIUpdateQuantityRequestDTO;
import DetalleReceta.msvc_DetalleReceta.exceptions.DetalleRecetaException;
import DetalleReceta.msvc_DetalleReceta.models.entities.DetalleReceta;
import DetalleReceta.msvc_DetalleReceta.models.Producto;
import DetalleReceta.msvc_DetalleReceta.models.Receta;
import DetalleReceta.msvc_DetalleReceta.repositories.DetalleRecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DetalleRecetaServiceImpl implements DetalleRecetaService {

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
    public boolean existsByIdReceta(Long idReceta) {
        return detalleRecetaRepository.existsByIdReceta(idReceta);
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

    @Transactional
    @Override
    public DetalleReceta detalleRecetaUpdateQuantity(Long id, DetalleRecetaIUpdateQuantityRequestDTO quantityRequest){

        DetalleReceta detalleReceta = detalleRecetaRepository.findById(id).orElseThrow(
                ()-> new DetalleRecetaException("Detalle de receta con el id "+id+" no encontrado")
        );

        if (quantityRequest.getCantidadUnidadMedida() == null || quantityRequest.getCantidadUnidadMedida() <= 0) {
            throw new DetalleRecetaException("La cantidad debe ser un número positivo");
        }

        detalleReceta.setCantidadUnidadMedida(quantityRequest.getCantidadUnidadMedida());
        return detalleRecetaRepository.save(detalleReceta);
    }

    @Transactional
    @Override
    public void deletarTodoByIdReceta (Long idReceta){
        // Verificar si la receta existe en la BD
        if (!detalleRecetaRepository.existsById(idReceta)) {
            throw new DetalleRecetaException("La receta con id " + idReceta + " no existe.");
        }

        // Verificar si tiene detalles
        if (!detalleRecetaRepository.existsByIdReceta(idReceta)) {
            throw new DetalleRecetaException("La receta con id " + idReceta + " no tiene detalles asociados.");
        }

        // Eliminar todos los detalles de la receta
        detalleRecetaRepository.deleteByIdReceta(idReceta);
    }

    @Transactional
    @Override
    public void deleteByidDetalleReceta(Long idDetalleReceta) {
        if (!detalleRecetaRepository.existsById(idDetalleReceta)) {
            throw new DetalleRecetaException("Detalle de receta con el id " + idDetalleReceta + " no encontrado");
        }
        detalleRecetaRepository.deleteById(idDetalleReceta);
    }


}
