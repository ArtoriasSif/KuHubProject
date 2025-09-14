package Recetas.msvc_Recetas.repositories;

import Recetas.msvc_Recetas.models.entities.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {
    boolean existsByNombreReceta(String nombreReceta);
    boolean existsByNombreRecetaAndIdRecetaNot(String capitalizado, Long idReceta);
    Receta findByNombreReceta(String nombreReceta);
}
