package msvc_Inventario.clients;


import msvc_Inventario.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "msvc-Producto", url = "http://localhost:8081/api/v1/producto")
public interface ProductoClientRest {

    @GetMapping("/id/{id}")
    Producto findProductoById(@PathVariable Long id);

    @GetMapping("/idByNombreProducto/{nombreProducto}")
    Producto getIdByNombreProducto(@PathVariable String nombreProducto);

    @GetMapping("/nombre/{nombreProducto}")
    Producto findProductoByName(@PathVariable String nombreProducto);

    @PostMapping
    Producto createProducto(@Validated @RequestBody Producto producto);

    @PostMapping("/buscar-por-ids") // Lo utilizo en Inventario
    List<Producto> obtenerPorIds(@RequestBody List<Long> ids);


}
