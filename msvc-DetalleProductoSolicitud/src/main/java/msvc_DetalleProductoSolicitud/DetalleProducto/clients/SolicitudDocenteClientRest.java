package msvc_DetalleProductoSolicitud.DetalleProducto.clients;

import msvc_DetalleProductoSolicitud.DetalleProducto.models.entity.SolicitudDocente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-SolicitudDocente", url = "http://localhost:8083/api/v1/solicituddocente")
public interface SolicitudDocenteClientRest {
    @GetMapping("/{idSolicitudDocente}")
    ResponseEntity<SolicitudDocente> findByIdSolicitudDocente(@PathVariable Long idSolicitudDocente);
}
