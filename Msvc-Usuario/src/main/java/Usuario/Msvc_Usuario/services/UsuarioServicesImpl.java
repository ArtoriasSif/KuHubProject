package Usuario.Msvc_Usuario.services;

import Usuario.Msvc_Usuario.exceptions.UsuarioException;
import Usuario.Msvc_Usuario.models.Usuario;
import Usuario.Msvc_Usuario.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicesImpl implements UsuarioServices{

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findByIdUsuario (Long idUsuario){
        return usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new UsuarioException("Usuario con de la id "+idUsuario+ "no encontrado" )
        );
    }

    public Usuario findByUsername (String username){
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new UsuarioException("Usuario con el nombre de usuario "+username+ "no encontrado" )
        );
    }

    public List<Usuario> findAllUsuarios (){
        return usuarioRepository.findAll();
    }

    public Usuario saveUsuario (Usuario usuario){

        if (usuarioRepository.existsByEmail(usuario.getEmail()) ){
            throw new UsuarioException("Ya existe un usuario vinculado a este Email");
        }
        if (usuarioRepository.existsByUsername(usuario.getUsername())){
            throw new UsuarioException("Ya existe un usuario con Nombre :"+usuario.getUsername()+
                    "registrado");
        }

        return usuarioRepository.save(usuario);
    }

    //FALTA TESTEAR AMBOS UPDATE
    public Usuario updateNombreUsuario(Usuario usuario){
        Usuario usuarioModificableName = findByIdUsuario(usuario.getIdUsuario());
        usuarioModificableName.setPrimeroNombre(usuario.getPrimeroNombre());
        usuarioModificableName.setSegundoNombre(usuario.getSegundoNombre());
        usuarioModificableName.setApellidoPaterno(usuario.getApellidoPaterno());
        usuarioModificableName.setApellidoMaterno(usuario.getApellidoMaterno());

        return usuarioRepository.save(usuarioModificableName);
    }

    public Usuario updateMailUsuario(Usuario usuario) {

        Usuario usuarioModificable = findByIdUsuario(usuario.getIdUsuario());
        usuarioModificable.setEmail(usuario.getEmail());

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new UsuarioException("Ya existe un usuario vinculado a este Email");
        }
        return usuarioRepository.save(usuarioModificable);

    }

}
