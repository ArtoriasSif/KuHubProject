package Seccion.Msvc_Seccion.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SeccionNameRequestDTO {

    @NotBlank(message = "El nombre de la sección no puede estar vacío")
    private String nombreSeccion;


}
