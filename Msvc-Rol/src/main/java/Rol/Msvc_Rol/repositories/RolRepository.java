package Rol.Msvc_Rol.repositories;

import Rol.Msvc_Rol.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Object> findByNombreRol(String capitalizado);
}
