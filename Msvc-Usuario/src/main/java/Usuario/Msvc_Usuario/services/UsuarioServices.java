package Usuario.Msvc_Usuario.services;

import GlobalServerPorts.dto.ClassesModelsDtos.RolDTO;
import GlobalServerPorts.dto.ClassesModelsDtos.SeccionDTO;
import GlobalServerPorts.dto.ClassesModelsDtos.UsuarioDTO;
import Usuario.Msvc_Usuario.dtos.UpdateIdSeccionesUsuarioByAdministratorRequestDTO;
import Usuario.Msvc_Usuario.dtos.UpdateUsuarioByAdministratorRequestDTO;
import Usuario.Msvc_Usuario.dtos.UpdateUsuarioByUsuarioRequestDTO;
import Usuario.Msvc_Usuario.models.entity.Usuario;

import java.util.List;

public interface UsuarioServices {

    Usuario findByIdUsuario (Long idUsuario);
    Usuario findByUsername (String username);
    List<Usuario> findAllUsuarios ();
    Usuario saveUsuario (Usuario usuario);
    Usuario updateUsuarioAdministrador
            (Long idUsuario,UpdateUsuarioByAdministratorRequestDTO request);
    Usuario UpdateAddIdSeccionesUsuarioByAdministrator
            (Long idUsuario, UpdateIdSeccionesUsuarioByAdministratorRequestDTO listaRequest);
    Usuario UpdateRemoveIdSeccionesUsuarioByAdministrator(
            Long idUsuario, UpdateIdSeccionesUsuarioByAdministratorRequestDTO listaRequest);
    Usuario UpdateUsuarioByUsuarioWithId
            (Long idUsuario, UpdateUsuarioByUsuarioRequestDTO requestDTO);
    Usuario UpdateUsuarioByUsuarioWithUsername(
            String userName, UpdateUsuarioByUsuarioRequestDTO requestDTO);
    void deleteUsuarioById(Long idUsuario);

    boolean existsByIdUsuario(Long idUsuario);



    // ===========================================================================================
    // Feign Clients
    // ===========================================================================================
    RolDTO getRolById(Long id);
    SeccionDTO getSeccionById(Long id);
    Boolean existeRol(Long id);
    // Agregar métodos para otros microservicios según necesidad

}
