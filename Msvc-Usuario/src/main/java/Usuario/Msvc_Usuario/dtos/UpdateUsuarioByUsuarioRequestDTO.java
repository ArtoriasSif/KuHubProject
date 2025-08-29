package Usuario.Msvc_Usuario.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateUsuarioByUsuarioRequestDTO {

    private String username;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{10,}$",
            message = "La contraseña debe tener al menos una mayúscula, un número y un mínimo de 10 caracteres"
    )
    private String password;
}
