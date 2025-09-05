package Usuario.Msvc_Usuario.models.entity;

import Rol.Msvc_Rol.models.RolNombre;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_rol")
    private Long idRol;

    // ⚠️ Este rol no se guarda en la BD. Se obtiene vía RolClientRest
    @Transient
    private RolNombre rol;

    @ElementCollection
    @CollectionTable(name = "usuario_secciones", joinColumns = @JoinColumn(name = "id_usuario"))
    @Column(name = "id_seccion")
    private List<Long> idSecciones = new ArrayList<>();

    @NotBlank(message = "El primer nombre no puede estar vacío")
    @Column(name = "primer_nombre", nullable = false)
    private String primeroNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "apellido_paterno", nullable = false)
    private String apellidoPaterno;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El correo electrónico no es válido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{10,}$",
            message = "La contraseña debe tener al menos una mayúscula, un número y un mínimo de 10 caracteres"
    )
    @Column(nullable = false)
    private String password;

    // ---------------- UserDetails ----------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (rol != null) {
            return List.of(new SimpleGrantedAuthority(rol.name()));
        }
        return List.of();
    }

    @Override
    public String getPassword() {
        return password; // corregido: antes estaba "clave"
    }

    @Override
    public String getUsername() {
        return username; // ahora usa username consistente con Spring Security
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // cuenta no expirada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // cuenta no bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // contraseña no expirada
    }

    @Override
    public boolean isEnabled() {
        return true; // cuenta habilitada
    }


}
