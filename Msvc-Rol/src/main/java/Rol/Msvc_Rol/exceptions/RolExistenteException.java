package Rol.Msvc_Rol.exceptions;

public class RolExistenteException extends RuntimeException {
    public RolExistenteException(String nombreRol) {
        super("Ya existe un rol con el nombre: " + nombreRol);
    }
}