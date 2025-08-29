package Usuario.Msvc_Usuario.services;

import Usuario.Msvc_Usuario.dtos.UpdateIdSeccionesUsuarioByAdministratorRequestDTO;
import Usuario.Msvc_Usuario.dtos.UpdateUsuarioByAdministratorRequestDTO;
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

}
