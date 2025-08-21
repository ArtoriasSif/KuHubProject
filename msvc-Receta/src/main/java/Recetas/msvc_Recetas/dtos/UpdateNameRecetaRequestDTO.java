package Recetas.msvc_Recetas.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateNameRecetaRequestDTO {

    @NotBlank(message = "El campo nombre receta no puede ser vacio")
    private String nombreReceta;

}
