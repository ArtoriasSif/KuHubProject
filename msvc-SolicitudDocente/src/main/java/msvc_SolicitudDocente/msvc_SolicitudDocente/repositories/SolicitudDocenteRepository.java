package msvc_SolicitudDocente.msvc_SolicitudDocente.repositories;

import msvc_SolicitudDocente.msvc_SolicitudDocente.models.entity.SolicitudDocente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudDocenteRepository extends JpaRepository <SolicitudDocente, Long>{
}
