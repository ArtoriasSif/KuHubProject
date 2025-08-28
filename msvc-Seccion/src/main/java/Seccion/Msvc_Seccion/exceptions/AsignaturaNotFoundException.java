package Seccion.Msvc_Seccion.exceptions;

import lombok.Getter;

@Getter
public class AsignaturaNotFoundException extends RuntimeException {

    private final Long idAsignatura;

    public AsignaturaNotFoundException(Long id) {
        super("no se encontró la asignatura con el id:"+id);
        this.idAsignatura = id;
    }

    public AsignaturaNotFoundException(Long id, Throwable cause) {
        super("No se encontró la asignatura con el id: " + id, cause);
        this.idAsignatura = id;
    }

}
