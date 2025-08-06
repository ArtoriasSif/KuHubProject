package msvc_SolicitudDocente.msvc_SolicitudDocente.clients;

import msvc_SolicitudDocente.msvc_SolicitudDocente.models.DetalleProductoSolicitud;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-DetalleProductoSolicitud", url = "http://localhost:8082/api/v1/detalleproductosolicitud")
public interface DetalleProductoSolicitudClientRest {

    @GetMapping
    List<DetalleProductoSolicitud> findAllDetalleProductoSolicitud();

}
