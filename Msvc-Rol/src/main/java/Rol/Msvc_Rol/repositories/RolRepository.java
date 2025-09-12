package Rol.Msvc_Rol.repositories;

import Rol.Msvc_Rol.models.RolNombre;
import Rol.Msvc_Rol.models.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombreRol(RolNombre nombreRol);

}
