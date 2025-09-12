package GlobalServerPorts.dto.ClassesModelsDtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolDTO {
    private Long idRol;
    private RolNombreDTO nombreRol;

    public enum RolNombreDTO {
        DOCENTE,
        DOCENTE_COORDINADOR,
        ADMINISTRADOR,
        CO_ADMINISTRADOR,
        ENCARGADO_BODEGA,
        AUXILIAR_ENCARGADO_BODEGA;


    }
}


