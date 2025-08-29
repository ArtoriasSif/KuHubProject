package Usuario.Msvc_Usuario.exceptions;

import lombok.Getter;

@Getter
public class UsernameUsuarioExistenteException extends RuntimeException {

  private final String username;

  public UsernameUsuarioExistenteException(String username) {
    super("Ya existe un usuario con nombre de usuario: " + username);
    this.username = username;
  }
}