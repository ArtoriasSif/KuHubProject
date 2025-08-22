package DetalleReceta.msvc_DetalleReceta.services;


import DetalleReceta.msvc_DetalleReceta.clients.ProductoClientRest;
import DetalleReceta.msvc_DetalleReceta.clients.RecetaClientRest;
import DetalleReceta.msvc_DetalleReceta.dtos.DetalleRecetaIUpdateQuantityRequestDTO;
import DetalleReceta.msvc_DetalleReceta.dtos.DetalleRecetaResponseDTO;
import DetalleReceta.msvc_DetalleReceta.exceptions.DetalleRecetaException;
import DetalleReceta.msvc_DetalleReceta.models.entities.DetalleReceta;
import DetalleReceta.msvc_DetalleReceta.models.Producto;
import DetalleReceta.msvc_DetalleReceta.models.Receta;
import DetalleReceta.msvc_DetalleReceta.repositories.DetalleRecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public DetalleReceta findByIdDetalleReceta(Long idDetalleReceta){
        return detalleRecetaRepository.findById(idDetalleReceta).orElseThrow(
                () -> new RuntimeException("Detalle Receta con id: "+idDetalleReceta+" no encontrado")
        );
    }

    @Transactional
    @Override
    public List<DetalleReceta> findAllDetalleRecetas() {
        return detalleRecetaRepository.findAll();
    }

    // Listar con Detalle DTO
    @Transactional(readOnly = true)
    @Override
    public List<DetalleRecetaResponseDTO> findAllDetalleRecetasConDetalles() {
        List<DetalleReceta> detalles = detalleRecetaRepository.findAll();

        if (detalles.isEmpty()) {
            throw new DetalleRecetaException("No hay detalles de receta registrados");
        }

        List<DetalleRecetaResponseDTO> responseDTOs = new ArrayList<>();
        Map<Long, Producto> cacheProductos = new HashMap<>();

        for (DetalleReceta d : detalles) {
            Producto p = cacheProductos.computeIfAbsent(d.getIdProducto(), id -> {
                try {
                    Producto producto = productoClientRest.findProductoById(id).getBody();
                    if (producto == null) {
                        throw new DetalleRecetaException("Producto con id " + id + " no encontrado");
                    }
                    return producto;
                } catch (Exception e) {
                    throw new DetalleRecetaException("Error al consultar producto con id " + id + ": " + e.getMessage());
                }
            });

            responseDTOs.add(new DetalleRecetaResponseDTO(
                    d.getIdDetalleReceta(),
                    d.getIdReceta(),
                    d.getIdProducto(),
                    p.getNombreProducto(),
                    p.getUnidadMedida(),
                    d.getCantidadUnidadMedida()
            ));
        }
        return responseDTOs;
    }

    //Lista todos detalles de una id recete con detalles DTO
    @Transactional
    @Override
    public List<DetalleRecetaResponseDTO> findAllByIdRecetaConDetalles(Long idReceta) {
        if(!detalleRecetaRepository.existsByIdReceta(idReceta)){
            throw new DetalleRecetaException("La receta con id "+idReceta+" no existe");
        }
        List<DetalleReceta> detalleReceta = detalleRecetaRepository.findAllByIdReceta(idReceta);
        List<DetalleRecetaResponseDTO> responseDTOs = new ArrayList<>();
        for (DetalleReceta d : detalleReceta) {

            Producto p = productoClientRest.findProductoById(d.getIdProducto()).getBody();
            assert p != null;

            responseDTOs.add(new DetalleRecetaResponseDTO(
                    d.getIdDetalleReceta(),
                    d.getIdReceta(),
                    p.getIdProducto(),
                    p.getNombreProducto(),
                    p.getUnidadMedida(),
                    d.getCantidadUnidadMedida()
            ));
        }
        return responseDTOs;

    }

    @Transactional(readOnly = true)
    @Override
    public DetalleRecetaResponseDTO findByIdRecetasConDetalles(Long idDetalleReceta) {
        DetalleReceta detalle = detalleRecetaRepository.findById(idDetalleReceta)
                .orElseThrow(() -> new DetalleRecetaException(
                        "El detalle de receta con id " + idDetalleReceta + " no existe"));

        Producto producto;
        try {
            producto = productoClientRest.findProductoById(detalle.getIdProducto()).getBody();
        } catch (Exception e) {
            throw new DetalleRecetaException("Producto con id " + detalle.getIdProducto() + " no encontrado");
        }
        assert producto != null;
        return new DetalleRecetaResponseDTO(
                detalle.getIdDetalleReceta(),
                detalle.getIdReceta(),
                detalle.getIdProducto(),
                producto.getNombreProducto(),
                producto.getUnidadMedida(),
                detalle.getCantidadUnidadMedida()
        );
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
            Producto producto = productoClientRest.findProductoById(detalleReceta.getIdProducto()).getBody();
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
