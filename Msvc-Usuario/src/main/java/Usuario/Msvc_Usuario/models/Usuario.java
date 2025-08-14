package Usuario.Msvc_Usuario.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name="usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario",nullable = false)
    private Long idUsuario;

    //Este dato tiene que ser Null y posteriomente se asignara el rol, o determinar una ID defaut para ROl, sin permisos
    @Column(name = "id_rol")
    private Long idRol;

    @NotBlank(message = "El primer nombre no puede estar vacío")
    @Column(name = "nombre_completo", nullable = false)
    private String primeroNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "apellido_materno" )
    private String apellidoMaterno;

    @NotBlank(message = "El apellido paterno no puede estar vacío")
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
}
