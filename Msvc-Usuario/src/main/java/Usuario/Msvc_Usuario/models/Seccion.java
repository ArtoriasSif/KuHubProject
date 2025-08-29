package Usuario.Msvc_Usuario.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Seccion {

    private Long idSeccion;
    private String nombreSeccion;
    private Long idAsignatura;
    private List<LocalDateTime> fechas = new ArrayList<>();
    private String periodo;
}
