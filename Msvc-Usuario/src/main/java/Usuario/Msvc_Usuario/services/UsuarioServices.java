package Usuario.Msvc_Usuario.services;

import Usuario.Msvc_Usuario.models.Usuario;

import java.util.List;

public interface UsuarioServices {

    Usuario findByIdUsuario (Long idUsuario);
    List<Usuario> findAllUsuarios ();
    Usuario save (Usuario usuario);
}
