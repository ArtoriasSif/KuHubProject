package Recetas.msvc_Recetas.Services;

import Recetas.msvc_Recetas.dtos.UpdateNameRecetaRequestDTO;
import Recetas.msvc_Recetas.models.entities.Receta;

import java.util.List;

public interface RecetaServices {
    List<Receta> findAllRecetas();
    Receta findByIdReceta(Long id);
    Receta save (Receta receta);
    Receta updateNameByIdReceta (Long idReceta, UpdateNameRecetaRequestDTO request);
    void deleteByIdReceta(Long idReceta);

}
