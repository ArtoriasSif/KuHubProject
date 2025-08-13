package Usuario.Msvc_Usuario.services;

import Usuario.Msvc_Usuario.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicesImpl implements UsuarioServices{

    @Autowired
    private UsuarioRepository usuarioRepository;
}
