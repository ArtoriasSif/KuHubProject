package Usuario.Msvc_Usuario.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequestDTO {
    @NotBlank(message = "El campo usuario no puede estar vacío")
    private String username; // o correo
    @NotBlank(message = "El campo contraseña no puede estar vacío")
    private String password;
}
