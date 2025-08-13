package Rol.Msvc_Rol.services;

import Rol.Msvc_Rol.dtos.RolUpdateNameResquest;
import Rol.Msvc_Rol.exceptions.RolException;
import Rol.Msvc_Rol.models.Rol;
import Rol.Msvc_Rol.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements  RolService{

    @Autowired
    private RolRepository rolRepository;

    @Transactional
    @Override
    public Rol findByIdRol(Long idRol) {
        return rolRepository.findById(idRol).orElseThrow(
                ()-> new RolException("Rol con el id "+idRol+" no encontrado")
        );
    }

    @Transactional
    @Override
    public List<Rol> findAllRoles() {
        return rolRepository.findAll();
    }

    @Transactional
    @Override
    public Rol saveRol(Rol rol) {

        String nombre = rol.getNombreRol().trim();
        String capitalizado = Arrays.stream(nombre.split("\\s+"))
                .map(p -> p.substring(0, 1).toUpperCase() + p.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

        if (rolRepository.findByNombreRol(capitalizado).isPresent()){
            throw new RolException("Ya existe un rol con el nombre: " + capitalizado);
        }
        rol.setNombreRol(capitalizado);
        return rolRepository.save(rol);
    }

    @Transactional
    @Override
    public Rol updateNameRolById(Long idRol, RolUpdateNameResquest nameResquest ){
        Rol rol = rolRepository.findById(idRol).orElseThrow(
                ()-> new RolException("Rol con el id "+idRol+" no encontrado")
        );

        String nombre = nameResquest.getNombreRol().trim();
        String capitalizado = Arrays.stream(nombre.split("\\s+"))
                .map(p -> p.substring(0, 1).toUpperCase() + p.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

        if (rolRepository.findByNombreRol(capitalizado).isPresent()){
            throw new RolException("Ya existe un rol con el nombre: " + capitalizado);
        }
        rol.setNombreRol(capitalizado);
        return rolRepository.save(rol);
    }



}
