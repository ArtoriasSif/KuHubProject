package Rol.Msvc_Rol.services;

import Rol.Msvc_Rol.dtos.RolNameRequestDTO;
import Rol.Msvc_Rol.exceptions.RolExistenteException;
import Rol.Msvc_Rol.exceptions.RolNotFoundException;
import Rol.Msvc_Rol.models.RolNombre;
import Rol.Msvc_Rol.models.entity.Rol;
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

    @Override
    public Rol findByNameRol(RolNombre nombreRol) {
        return rolRepository.findByNombreRol(nombreRol)
                .orElseThrow(() -> new RolNotFoundException(nombreRol.name()));
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
    public Rol saveRol(RolNameRequestDTO requestDTO) {

        String capitalizado = StringUtils.capitalizarPalabras(requestDTO.getNombreRol());

        String enumKey = StringUtils.formatearParaEnum(requestDTO.getNombreRol());

        RolNombre rolEnum;
        try {
            rolEnum = RolNombre.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol inválido: " + capitalizado);
        }

        if (rolRepository.findByNombreRol(rolEnum).isPresent()) {
            throw new RolExistenteException(capitalizado);
        }

        Rol rol = new Rol();
        rol.setNombreRol(RolNombre.valueOf(enumKey));
        return rolRepository.save(rol);
    }

    @Transactional
    @Override
    public Rol updateNameRolByName(String nombreRolExistente, RolNameRequestDTO requestDTO) {

        String capitalizado = StringUtils.capitalizarPalabras(requestDTO.getNombreRol());
        String enumKeyNuevo = StringUtils.formatearParaEnum(requestDTO.getNombreRol());

        RolNombre rolEnumNuevo;
        try {
            rolEnumNuevo = RolNombre.valueOf(enumKeyNuevo);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol inválido: " + capitalizado);
        }

        if (rolRepository.findByNombreRol(rolEnumNuevo).isPresent()) {
            throw new RolExistenteException(capitalizado);
        }
        Rol rolExistente = rolRepository.findByNombreRol(RolNombre.valueOf(StringUtils.formatearParaEnum(nombreRolExistente)))
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombreRolExistente));

        rolExistente.setNombreRol(rolEnumNuevo);
        return rolRepository.save(rolExistente);

    }


    @Transactional
    @Override
    public Rol updateNameRolById(Long idRol, RolNameRequestDTO nameRequest ){
        Rol rol = rolRepository.findById(idRol).orElseThrow(
                () -> new RolNotFoundException(idRol));

        // Capitalizamos y formateamos para enum
        String capitalizado = StringUtils.capitalizarPalabras(nameRequest.getNombreRol());
        String enumKey = StringUtils.formatearParaEnum(capitalizado);

        // Convertimos a enum
        RolNombre rolEnum;
        try {
            rolEnum = RolNombre.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol inválido: " + capitalizado);
        }

        // Verificamos que no exista otro rol con el mismo enum
        if (rolRepository.findByNombreRol(rolEnum).isPresent()) {
            throw new RolExistenteException(capitalizado);
        }

        rol.setNombreRol(rolEnum);
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
