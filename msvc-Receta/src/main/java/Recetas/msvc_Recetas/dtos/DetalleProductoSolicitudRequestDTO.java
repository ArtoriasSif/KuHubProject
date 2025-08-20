package Recetas.msvc_Recetas.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetalleProductoSolicitudRequestDTO {

    @Column(name =  "id_detalle_producto_solicitud",nullable = false,unique = true)
    private Long    idDetalleProductoSolicitud;

    @NotNull(message = "El campo id solicitud docente no puede ser vacio")
    private Long    idSolicitudDocente;

    @NotNull(message = "El campo id producto no puede ser vacio")
    private Long    idProducto;

    @NotNull(message = "El campo cantidad unidad medida no puede ser vacio")
    @Digits(integer = 7, fraction = 3, message = "Máximo 3 decimales permitidos")
    private Float cantidadUnidadMedida;
}
