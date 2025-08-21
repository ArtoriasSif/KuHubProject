package msvc_DetalleProductoSolicitud.DetalleProducto.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class DetalleProductoSolicitudUpdateQuantityRequestDTO {

    @Digits(integer = 7, fraction = 3, message = "Máximo 3 decimales permitidos")
    @NotNull(message = "El campo id cantidad unidad medida no puede ser vacio")
    private Float cantidadUnidadMedida;
}
