package Seccion.Msvc_Seccion.repositories;

import Seccion.Msvc_Seccion.models.entity.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Long> {
    boolean existsByNombreSeccion(String capitalizado);
    boolean existsByNombreSeccionAndIdAsignatura(String nombreSeccion, Long idAsignatura);
    Optional<Seccion> findByNombreSeccion(String nombreSeccion);
}
