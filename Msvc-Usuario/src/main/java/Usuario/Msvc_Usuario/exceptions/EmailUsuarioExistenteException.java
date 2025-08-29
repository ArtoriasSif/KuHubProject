package Usuario.Msvc_Usuario.exceptions;

import lombok.Getter;

@Getter
public class EmailUsuarioExistenteException extends RuntimeException {

    private final String email;

    public EmailUsuarioExistenteException(String email) {
        super("Ya existe un usuario vinculado a este email: " + email);
        this.email = email;
    }
}