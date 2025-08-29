package Usuario.Msvc_Usuario.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateIdSeccionesUsuarioByAdministratorRequestDTO {

    private List<Long> idSecciones = new ArrayList<>();


}
