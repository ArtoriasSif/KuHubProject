package msvc_SolicitudDocente.msvc_SolicitudDocente.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDocenteResponseDTO {

    private Long idSolicitudDocente;

    //agregar id Docente cuando creado el msvc roles o usuario

    private Long numeroSemana ;

    private Long numeroTaller;

    private Long cantidadPersonas;

    private String descripcionSemana;

    private String sesion;

    private String nombreAsignatura;

    private Date fechaProgramada;

    //retornar lista con detalles de productos solicitados

    private List <DetalleProductoSolicitudResponseDTO> detallesProductoSolicitud;

}
