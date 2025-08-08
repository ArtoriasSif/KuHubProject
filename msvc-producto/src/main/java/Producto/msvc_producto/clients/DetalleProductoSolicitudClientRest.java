package Producto.msvc_producto.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-DetalleProductoSolicitud", url = "http://localhost:8082/api/v1/detalleproductosolicitud")
public interface DetalleProductoSolicitudClientRest {

    @GetMapping("/existeProducto/{nombreProducto}")
    Boolean existeProductoEnDetalle(@PathVariable String nombreProducto);

    @GetMapping("/existeProductoId/{idProducto}")
    Boolean existeProductoIdEnDetalle(@PathVariable Long idProducto);
}
