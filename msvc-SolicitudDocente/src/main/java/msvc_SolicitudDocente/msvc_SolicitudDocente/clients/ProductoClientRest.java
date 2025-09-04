package msvc_SolicitudDocente.msvc_SolicitudDocente.clients;

import msvc_SolicitudDocente.msvc_SolicitudDocente.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-Producto", url = "http://localhost:8081/api/v1/producto")
public interface ProductoClientRest {

    @GetMapping("/id/{id}")
    Producto findProductoById(@PathVariable Long id);
}

