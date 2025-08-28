package Seccion.Msvc_Seccion.repositories;

import Seccion.Msvc_Seccion.models.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Long> {
}
