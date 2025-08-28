package Rol.Msvc_Rol.exceptions;

public class RolNotFoundException extends RuntimeException {

    public RolNotFoundException(Long idRol) {
        super("Rol con el id " + idRol + " no encontrado");
    }

    public RolNotFoundException(String nombreRol) {
        super("Rol con el nombre '" + nombreRol + "' no encontrado");
    }
}
