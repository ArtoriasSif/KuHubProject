package msvc_Inventario.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Producto {

    private Long idProducto;

    private String nombreProducto;

    private String unidadMedida;

}
