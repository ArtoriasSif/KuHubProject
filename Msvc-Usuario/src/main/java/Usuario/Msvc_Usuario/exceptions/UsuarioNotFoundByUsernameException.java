package Usuario.Msvc_Usuario.exceptions;

import lombok.Getter;

@Getter
public class UsuarioNotFoundByUsernameException extends RuntimeException {

    private final String username;

    public UsuarioNotFoundByUsernameException(String username) {
        super("No se encontró el usuario con el nombre de usuario: " + username);
        this.username = username;
    }

    public UsuarioNotFoundByUsernameException(String username, Throwable cause) {
        super("No se encontró el usuario con el nombre de usuario: " + username, cause);
        this.username = username;
    }
}
