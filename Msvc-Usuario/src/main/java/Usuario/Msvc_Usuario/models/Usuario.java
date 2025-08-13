package Usuario.Msvc_Usuario.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

    @Column(name="nombre_completo",nullable = false)
    private String primeroNombre;

    @Column(name="segundo_nombre")
    private String segundoNombre;

    @Column(name="apellido_materno",nullable = false)
    private String apellidoMaterno;

    @Column(name="apellido_paterno",nullable = false)
    private String apellidoPaterno;

    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
