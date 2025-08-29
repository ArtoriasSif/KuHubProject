package Usuario.Msvc_Usuario.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateUsuarioByAdministratorRequestDTO {

    private Long idRol;
    private List<Long> idSecciones = new ArrayList<>();
    private String primeroNombre;
    private String segundoNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    @Email(message = "El correo electrónico no es válido")
    private String email;
    private String username;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{10,}$",
            message = "La contraseña debe tener al menos una mayúscula, un número y un mínimo de 10 caracteres"
    )
    private String password;

}
