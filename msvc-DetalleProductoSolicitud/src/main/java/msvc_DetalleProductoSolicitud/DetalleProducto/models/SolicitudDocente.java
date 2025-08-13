package msvc_DetalleProductoSolicitud.DetalleProducto.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SolicitudDocente {

    private Long idSolicitudDocente;

    private Long numeroSemana ;

    private Long numeroTaller;

    private Long cantidadPersonas;

    private String descripcionSemana;

    private String sesion;

    private String nombreAsignatura;

    private Date fechaProgramada;

}
