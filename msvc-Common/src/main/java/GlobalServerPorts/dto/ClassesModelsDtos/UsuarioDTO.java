package GlobalServerPorts.dto.ClassesModelsDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long idUsuario;
    private Long idRol;
    private String rol; // antes RolNombre, ahora String para desacoplar del microservicio Rol
    private List<Long> idSecciones;

    private String primeroNombre;
    private String segundoNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String username;
    private String password; // si no lo necesitas enviarlo, se puede omitir
}

