package Recetas.msvc_Recetas.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetalleReceta {

    private Long idDetalleReceta;

    private Long idReceta;

    private Long idProducto;

    private Float cantidadUnidadMedida;
}
