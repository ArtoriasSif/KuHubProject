package Asignatura.Msvc_Asignatura.repositories;

import Asignatura.Msvc_Asignatura.models.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    Optional<Asignatura> findByNombreAsignatura(String nombreAsignatura);
    boolean existsByNombreAsignatura(String capitalizado);
}
