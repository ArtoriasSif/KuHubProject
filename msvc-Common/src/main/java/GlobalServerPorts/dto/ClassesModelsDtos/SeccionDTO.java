package GlobalServerPorts.dto.ClassesModelsDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeccionDTO {
    private Long idSeccion;
    private String nombreSeccion;
    private Long idAsignatura;
    private List<LocalDateTime> fechas;
    private String periodo;
}

