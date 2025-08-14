package Usuario.Msvc_Usuario.services;

import Usuario.Msvc_Usuario.models.Usuario;

import java.util.List;

public interface UsuarioServices {

    Usuario findByIdUsuario (Long idUsuario);
    Usuario findByUsername (String username);
    List<Usuario> findAllUsuarios ();
    Usuario saveUsuario (Usuario usuario);
}
