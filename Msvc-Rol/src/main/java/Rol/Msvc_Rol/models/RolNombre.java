package Rol.Msvc_Rol.models;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum RolNombre {
    DOCENTE,
    DOCENTE_COORDINADOR,
    ADMINISTRADOR,
    CO_ADMINISTRADOR,
    ENCARGADO_BODEGA,
    AUXILIAR_ENCARGADO_BODEGA;

    // Tanto en Java y en msj JSON la respuesta estará capitalizada, pero en la BBDD sigue el formato de Enum
    @Override
    @JsonValue
    public String toString() {
        return Arrays.stream(this.name().split("_"))
                .map(p -> p.substring(0, 1).toUpperCase() + p.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
