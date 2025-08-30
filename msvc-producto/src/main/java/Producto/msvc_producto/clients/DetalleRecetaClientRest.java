package Producto.msvc_producto.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "msvc-Receta", url = "http://localhost:8088/api/v1/detallereceta")

public interface DetalleRecetaClientRest {

    @GetMapping("/existeProducto/{idProducto}")
    boolean existsByIdProducto(@PathVariable Long idProducto);

}
