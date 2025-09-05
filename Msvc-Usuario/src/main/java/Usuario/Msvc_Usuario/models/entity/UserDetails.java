package Usuario.Msvc_Usuario.models.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserDetails {

    // Retorna los roles o permisos del usuario
    Collection<? extends GrantedAuthority> getAuthorities();

    // Retorna la contraseña
    String getPassword();

    // Retorna el nombre de usuario (o correo, o identificador único)
    String getUsername();

    // Indica si la cuenta NO ha expirado
    boolean isAccountNonExpired();

    // Indica si la cuenta NO está bloqueada
    boolean isAccountNonLocked();

    // Indica si la contraseña NO ha expirado
    boolean isCredentialsNonExpired();

    // Indica si la cuenta está habilitada
    boolean isEnabled();
}
