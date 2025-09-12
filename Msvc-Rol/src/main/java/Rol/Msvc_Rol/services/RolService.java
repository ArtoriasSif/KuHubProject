package Rol.Msvc_Rol.services;

import Rol.Msvc_Rol.dtos.RolNameRequestDTO;
import Rol.Msvc_Rol.models.RolNombre;
import Rol.Msvc_Rol.models.entity.Rol;

import java.util.List;

public interface RolService {
    Rol findByIdRol(Long idRol);
    Rol findByNameRol(RolNombre nombreRol);
    List<Rol> findAllRoles();
    Rol saveRol(RolNameRequestDTO requestDTO);
    Rol updateNameRolById(Long idRol, RolNameRequestDTO nameResquest);
    Rol updateNameRolByName(String nombreRol, RolNameRequestDTO nameResquest);
    void deleteByIdRol(Long idRol);
    boolean existeRolById(Long idRol);
}
