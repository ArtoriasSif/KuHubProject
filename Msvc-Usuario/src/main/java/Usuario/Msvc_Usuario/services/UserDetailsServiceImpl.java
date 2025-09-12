package Usuario.Msvc_Usuario.services;

import GlobalServerPorts.FeignConfig;
import GlobalServerPorts.dto.ClassesModelsDtos.RolDTO;
import GlobalServerPorts.dto.InterfacesFeignClientEmpty.RolClientRest;
import Usuario.Msvc_Usuario.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioServices usuarioService; // servicio para buscar usuarios

    private final FeignConfig feignConfig;

    public UserDetailsServiceImpl(FeignConfig feignConfig) {
        this.feignConfig = feignConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        // Cliente Feign para roles
        RolClientRest rolClient = feignConfig.getClient("rol", RolClientRest.class);
        RolDTO rolDto = rolClient.findByIdRol(usuario.getIdRol());

        // 👇 Siempre prefijo ROLE_ y uso el enum name()
        String roleName = (rolDto != null && rolDto.getNombreRol() != null)
                ? "ROLE_" + rolDto.getNombreRol().name()
                : "ROLE_USER";

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority(roleName))
        );
    }
}
