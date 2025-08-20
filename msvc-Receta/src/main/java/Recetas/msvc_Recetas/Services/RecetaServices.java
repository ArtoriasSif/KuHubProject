package Recetas.msvc_Recetas.Services;

import Recetas.msvc_Recetas.models.Receta;

import java.util.List;

public interface RecetaServices {
    List<Receta> findAllRecetas();
    Receta findByIdReceta(Long id);
    Receta save (Receta receta);

}
