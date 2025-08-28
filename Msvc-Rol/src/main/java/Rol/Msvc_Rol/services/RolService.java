package Rol.Msvc_Rol.services;

import Rol.Msvc_Rol.dtos.RolUpdateNameResquest;
import Rol.Msvc_Rol.models.Rol;

import java.util.List;

public interface RolService {
    Rol findByIdRol(Long idRol);
    List<Rol> findAllRoles();
    Rol saveRol(Rol rol);
    Rol updateNameRolById(Long idRol, RolUpdateNameResquest nameResquest);
    Rol updateNameRolByName(String nombreRol, RolUpdateNameResquest nameResquest);
    void deleteByIdRol(Long idRol);
}
