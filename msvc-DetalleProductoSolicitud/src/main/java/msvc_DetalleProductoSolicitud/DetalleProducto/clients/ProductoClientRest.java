package msvc_DetalleProductoSolicitud.DetalleProducto.clients;

import msvc_DetalleProductoSolicitud.DetalleProducto.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-Producto", url = "http://localhost:8081/api/v1/producto")
public interface ProductoClientRest {

    @GetMapping("/{id}")
    Producto findProductoById(@PathVariable Long id);

}
