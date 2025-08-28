package Asignatura.Msvc_Asignatura.repositories;

import Asignatura.Msvc_Asignatura.models.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repositories extends JpaRepository<Asignatura, Long> {
}
