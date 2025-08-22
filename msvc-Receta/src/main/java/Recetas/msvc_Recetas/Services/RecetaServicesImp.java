package Recetas.msvc_Recetas.Services;

import Recetas.msvc_Recetas.clients.DetalleRecetaClientRest;
import Recetas.msvc_Recetas.dtos.DetalleRecetaResponseDTO;
import Recetas.msvc_Recetas.dtos.RecetaResponseDTO;
import Recetas.msvc_Recetas.dtos.UpdateNameRecetaRequestDTO;
import Recetas.msvc_Recetas.exceptions.RecetaException;
import Recetas.msvc_Recetas.models.entities.Receta;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Recetas.msvc_Recetas.repositories.RecetaRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecetaServicesImp implements RecetaServices{

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private DetalleRecetaClientRest detalleRecetaClientRest;

    //Sin Detalles
    @Transactional
    @Override
    public List<Receta> findAllRecetas() {
        return recetaRepository.findAll();
    }

    @Transactional
    @Override
    public Receta findByIdReceta(Long id) {
        return recetaRepository.findById(id).orElseThrow(
                ()-> new RecetaException("La receta no existe")
        );
    }

    //Con Detalles
    @Transactional
    @Override
    public RecetaResponseDTO findByIdRecetasConDetalles(Long idReceta) {
        Receta receta = recetaRepository.findById(idReceta).orElseThrow(
                () -> new RecetaException("La receta con el id " + idReceta + " no existe")
        );

        List<DetalleRecetaResponseDTO> detalles = detalleRecetaClientRest.findAllByIdRecetaConDetalles(idReceta).getBody();

        return new RecetaResponseDTO(
                receta.getIdReceta(),
                receta.getNombreReceta(),
                detalles
        );
    }



    @Transactional
    @Override
    public List<RecetaResponseDTO> findAllRecetasConDetalles() {
        log.info("Iniciando búsqueda de todas las recetas con detalles");

        List<Receta> recetaModel = recetaRepository.findAll();

        if (recetaModel.isEmpty()) {
            log.warn("No existen recetas registradas en la base de datos");
            throw new RuntimeException("No existen recetas registradas");
        }

        List<RecetaResponseDTO> recetaDTOs = new ArrayList<>();

        for (Receta recMod : recetaModel) {
            List<DetalleRecetaResponseDTO> detalles;
            try {
                detalles = Optional.ofNullable(
                        detalleRecetaClientRest.findAllByIdRecetaConDetalles(recMod.getIdReceta()).getBody()
                ).orElse(Collections.emptyList());
            } catch (FeignException e) {
                log.error("Servicio DetalleReceta no disponible para receta {}", recMod.getIdReceta());
                detalles = Collections.emptyList();
            }

            log.info("Receta {} tiene {} detalles", recMod.getNombreReceta(), detalles.size());

            recetaDTOs.add(new RecetaResponseDTO(
                    recMod.getIdReceta(),
                    recMod.getNombreReceta(),
                    detalles
            ));
        }

        log.info("Finalizó búsqueda de recetas, total: {}", recetaDTOs.size());
        return recetaDTOs;
    }


    @Transactional
    @Override
    public Receta save (Receta receta){

        String nombre = receta.getNombreReceta().trim();
        String capitalizado = Arrays.stream(nombre.split("\\s+"))
                .map(p -> p.substring(0, 1).toUpperCase() + p.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

        if (recetaRepository.existsByNombreReceta(capitalizado)){
            throw new RecetaException("El nombre receta ya existe");
        }

        receta.setNombreReceta(capitalizado);

        return recetaRepository.save(receta);
    }

    @Transactional
    @Override
    public Receta updateNameByIdReceta(Long idReceta, UpdateNameRecetaRequestDTO request) {
        Receta receta = recetaRepository.findById(idReceta).orElseThrow(
                () -> new RecetaException("La receta con el id " + idReceta + " no existe")
        );

        // normalizar y capitalizar
        String nombre = request.getNombreReceta().trim();
        String capitalizado = Arrays.stream(nombre.split("\\s+"))
                .map(p -> p.substring(0, 1).toUpperCase() + p.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

        // validar que no exista otra receta con ese nombre
        if (recetaRepository.existsByNombreRecetaAndIdRecetaNot(capitalizado, idReceta)) {
            throw new RecetaException("Ya existe otra receta con el nombre: " + capitalizado);
        }

        // si el nombre es igual al actual,no actializa
        if (capitalizado.equals(receta.getNombreReceta())) {
            throw new RecetaException("El nombre es el mismo que ya tiene la receta.");
        }

        receta.setNombreReceta(capitalizado);
        return recetaRepository.save(receta);
    }

    @Transactional
    @Override
    public void deleteByIdReceta(Long idReceta){

        if (!recetaRepository.existsById(idReceta)) {
            throw new RecetaException("La receta con el id " + idReceta + " no existe");
        }
        // Eliminar todos los detalles de la receta
        if (!detalleRecetaClientRest.existsByIdReceta(idReceta)) {
            throw new RecetaException("La receta con el id " + idReceta + " no tiene detalles asociados");
        }
        // Eliminar detalles de la receta
        detalleRecetaClientRest.deleteDetallesByReceta(idReceta);
        // Eliminar la receta
        recetaRepository.deleteById(idReceta);
    }

}
