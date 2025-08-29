package Rol.Msvc_Rol.services;

import Rol.Msvc_Rol.dtos.RolUpdateNameResquest;
import Rol.Msvc_Rol.exceptions.RolExistenteException;
import Rol.Msvc_Rol.exceptions.RolNotFoundException;
import Rol.Msvc_Rol.models.Rol;
import Rol.Msvc_Rol.repositories.RolRepository;
import Rol.Msvc_Rol.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RolServiceImpl implements  RolService{

    @Autowired
    private RolRepository rolRepository;

    @Transactional
    @Override
    public Rol findByIdRol(Long idRol) {
        return rolRepository.findById(idRol).orElseThrow(
                ()-> new RolNotFoundException(idRol));
    }

    @Transactional
    @Override
    public List<Rol> findAllRoles() {
        return rolRepository.findAll();
    }

    @Override
    public boolean existeRolById(Long idRol) {
        return rolRepository.existsById(idRol);
    }

    @Transactional
    @Override
    public Rol saveRol(Rol rol) {

        String capitalizado = StringUtils.capitalizarPalabras(rol.getNombreRol());

        if (rolRepository.findByNombreRol(capitalizado).isPresent()){
            throw new RolExistenteException(capitalizado);
        }
        rol.setNombreRol(capitalizado);
        return rolRepository.save(rol);
    }

    @Transactional
    @Override
    public Rol updateNameRolByName(String nombreRol, RolUpdateNameResquest nameResquest) {

        Rol rol = rolRepository.findByNombreRol(nombreRol).orElseThrow(
                () -> new RolNotFoundException(nombreRol)
        );

        String capitalizado = StringUtils.capitalizarPalabras(nameResquest.getNombreRol());
        if (rolRepository.findByNombreRol(capitalizado).isPresent()) {
            throw new RolExistenteException(capitalizado);
        }

        rol.setNombreRol(capitalizado);
        return rolRepository.save(rol);
    }


    @Transactional
    @Override
    public Rol updateNameRolById(Long idRol, RolUpdateNameResquest nameResquest ){
        Rol rol = rolRepository.findById(idRol).orElseThrow(
                ()-> new RolNotFoundException(idRol));

        String capitalizado = StringUtils.capitalizarPalabras(nameResquest.getNombreRol());
        if (rolRepository.findByNombreRol(capitalizado).isPresent()){
            throw new RolExistenteException(capitalizado);
        }
        rol.setNombreRol(capitalizado);
        return rolRepository.save(rol);
    }

    //METODO PELIGROSO, SI ELIMINA UN ROL PUEDE IMPEDIR ACESSO A INTERFACE
    @Transactional
    @Override
    public void deleteByIdRol(Long idRol){
        if (!rolRepository.existsById(idRol)) {
            throw new RolNotFoundException(idRol);
        }
        rolRepository.deleteById(idRol);
    }




}
