package DetalleReceta.msvc_DetalleReceta.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetalleRecetaIUpdateQuantityRequestDTO {

    @Digits(integer = 7, fraction = 3, message = "Máximo 3 decimales permitidos")
    @NotNull(message = "El campo id cantidad unidad medida no puede ser vacio")
    private Float cantidadUnidadMedida;
}
