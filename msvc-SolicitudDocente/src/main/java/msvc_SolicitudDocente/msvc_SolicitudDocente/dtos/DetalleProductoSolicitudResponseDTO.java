package msvc_SolicitudDocente.msvc_SolicitudDocente.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetalleProductoSolicitudResponseDTO {

    private Long idDetalleProductoSolicitud;
    private Long idSolicitudDocente;
    private Long idProducto;
    private String nombreProducto;
    private Integer cantidadUnidadMedida;

}
