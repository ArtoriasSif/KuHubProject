package msvc_DetalleProductoSolicitud.DetalleProducto.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class DetalleProductoSolicitudUpdateQuantityRequest {
    @NotNull(message = "La cantidad de la unidad de medida no puede ser nula")
    private Integer cantidadUnidadMedida;
}
