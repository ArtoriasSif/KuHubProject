package Rol.Msvc_Rol.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {


    public static String capitalizarPalabras(String texto) {
        if (texto == null || texto.isBlank()) return texto;

        return Arrays.stream(texto.trim().split("\\s+"))
                .map(p -> p.substring(0, 1).toUpperCase() + p.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    // Nuevo método: formatea para Enum y elimina espacios al inicio y final
    public static String formatearParaEnum(String texto) {
        if (texto == null || texto.isBlank()) return "";
        // 1️⃣ Trim para quitar espacios iniciales/finales
        // 2️⃣ Convertir a mayúsculas
        // 3️⃣ Reemplazar espacios intermedios por _
        return texto.trim()           // quita espacios al inicio y final
                .toUpperCase()    // todo mayúscula
                .replaceAll("\\s+", "_"); // reemplaza cualquier secuencia de espacios por _
    }

}