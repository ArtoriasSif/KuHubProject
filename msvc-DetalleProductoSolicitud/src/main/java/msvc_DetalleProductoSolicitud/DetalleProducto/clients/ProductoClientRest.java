package msvc_DetalleProductoSolicitud.DetalleProducto.clients;

import msvc_DetalleProductoSolicitud.DetalleProducto.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-Producto", url = "http://localhost:8081/api/v1/producto")
public interface ProductoClientRest {

    @GetMapping("/id/{id}")
    Producto findProductoById(@PathVariable Long id);

    @GetMapping("/nombre/{nombreProducto}")
    ResponseEntity<Producto> findProductoByName(@PathVariable String nombreProducto);

    @GetMapping("/existeProducto/nombre/{nombreProducto}")
    Boolean existsByNombreProducto(@PathVariable String nombreProducto);

    @GetMapping("/existeProducto/id/{idProducto}")
    Boolean existeProductoId(@PathVariable Long idProducto);
}

