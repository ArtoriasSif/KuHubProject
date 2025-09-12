package GlobalServerPorts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
@ConfigurationProperties(prefix = "microservicios")
@Getter
@Setter
public class MicroserviciosConfig {

    private Msvc producto;
    private Msvc detalleProductoSolicitud;
    private Msvc solicitudDocente;
    private Msvc usuario;
    private Msvc inventario;
    private Msvc rol;
    private Msvc receta;
    private Msvc detalleReceta;
    private Msvc asignatura;
    private Msvc movimientos;
    private Msvc seccion;

    @Getter
    @Setter
    public static class Msvc {
        private String name; // Ej: "msvc-producto"
        private String url;  // Ej: "http://localhost:8081/api/v1"
    }

    // Obtener URL según audience
    public String getUrlByAudience(String audience) {
        if (audience == null) return "";
        switch (audience.toLowerCase(Locale.ROOT)) {
            case "msvc-producto": return producto != null ? producto.getUrl() : "";
            case "msvc-detalle-producto-solicitud": return detalleProductoSolicitud != null ? detalleProductoSolicitud.getUrl() : "";
            case "msvc-solicitud-docente": return solicitudDocente != null ? solicitudDocente.getUrl() : "";
            case "msvc-usuario": return usuario != null ? usuario.getUrl() : "";
            case "msvc-inventario": return inventario != null ? inventario.getUrl() : "";
            case "msvc-rol": return rol != null ? rol.getUrl() : "";
            case "msvc-receta": return receta != null ? receta.getUrl() : "";
            case "msvc-detalle-receta": return detalleReceta != null ? detalleReceta.getUrl() : "";
            case "msvc-asignatura": return asignatura != null ? asignatura.getUrl() : "";
            case "msvc-movimientos": return movimientos != null ? movimientos.getUrl() : "";
            case "msvc-seccion": return seccion != null ? seccion.getUrl() : "";
            default: return "";
        }
    }

    // Obtener name según audience
    public String getNameByAudience(String audience) {
        if (audience == null) return "";
        switch (audience.toLowerCase(Locale.ROOT)) {
            case "msvc-producto": return producto != null ? producto.getName() : "";
            case "msvc-detalle-producto-solicitud": return detalleProductoSolicitud != null ? detalleProductoSolicitud.getName() : "";
            case "msvc-solicitud-docente": return solicitudDocente != null ? solicitudDocente.getName() : "";
            case "msvc-usuario": return usuario != null ? usuario.getName() : "";
            case "msvc-inventario": return inventario != null ? inventario.getName() : "";
            case "msvc-rol": return rol != null ? rol.getName() : "";
            case "msvc-receta": return receta != null ? receta.getName() : "";
            case "msvc-detalle-receta": return detalleReceta != null ? detalleReceta.getName() : "";
            case "msvc-asignatura": return asignatura != null ? asignatura.getName() : "";
            case "msvc-movimientos": return movimientos != null ? movimientos.getName() : "";
            case "msvc-seccion": return seccion != null ? seccion.getName() : "";
            default: return "";
        }
    }

    // Obtener Msvc por nombre
    public Msvc getMsvcByName(String nombre) {
        if (nombre == null) return null;
        switch (nombre.toLowerCase(Locale.ROOT)) {
            case "producto": return producto;
            case "detalleproductosolicitud": return detalleProductoSolicitud;
            case "solicituddocente": return solicitudDocente;
            case "usuario": return usuario;
            case "inventario": return inventario;
            case "rol": return rol;
            case "receta": return receta;
            case "detallereceta": return detalleReceta;
            case "asignatura": return asignatura;
            case "movimientos": return movimientos;
            case "seccion": return seccion;
            default: return null;
        }
    }
}




