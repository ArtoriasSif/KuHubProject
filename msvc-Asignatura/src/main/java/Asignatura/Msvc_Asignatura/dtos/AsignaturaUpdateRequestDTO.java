package Asignatura.Msvc_Asignatura.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class AsignaturaUpdateRequestDTO {

    @NotBlank(message = "El nuevo nombre de la asignatura no puede estar vacío")
    private String nombreAsignatura;
}
