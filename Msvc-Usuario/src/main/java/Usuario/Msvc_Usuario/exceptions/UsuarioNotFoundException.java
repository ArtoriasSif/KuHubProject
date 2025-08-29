package Usuario.Msvc_Usuario.exceptions;

import lombok.Getter;

@Getter
public class UsuarioNotFoundException extends RuntimeException {

    private final Long idUsuario;

    public UsuarioNotFoundException(Long id) {
        super("No se encontró el usuario con el id: " + id);
        this.idUsuario = id;
    }

    public UsuarioNotFoundException(Long id, Throwable cause) {
        super("No se encontró el usuario con el id: " + id, cause);
        this.idUsuario = id;
    }
}
