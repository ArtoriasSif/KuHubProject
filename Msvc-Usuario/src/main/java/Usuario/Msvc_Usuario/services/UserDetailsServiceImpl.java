package Usuario.Msvc_Usuario.services;

import Rol.Msvc_Rol.models.RolNombre;
import Usuario.Msvc_Usuario.clients.RolClientRest;
import Usuario.Msvc_Usuario.models.Rol;
import Usuario.Msvc_Usuario.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService; // reutilizas tu servicio de negocio

    @Autowired
    private RolClientRest rolClientRest;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario;
        try {
            // 1️⃣ Buscar usuario
            usuario = usuarioService.findByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username, e);
        }

        try {
            // 2️⃣ Obtener rol desde msvc-rol y extraer el body
            Rol rol = rolClientRest.findByIdRol(usuario.getIdRol()).getBody();

            if (rol == null) {
                throw new UsernameNotFoundException("Rol no encontrado para el usuario: " + username);
            }

            // 3️⃣ Asignar rol al usuario
            usuario.setRol(RolNombre.valueOf(rol.getNombreRol()));

        } catch (Exception e) {
            throw new UsernameNotFoundException("Error al obtener rol desde msvc-rol para el usuario: " + username, e);
        }

        return usuario;
    }

}
