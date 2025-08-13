package msvc_Inventario.clients;


import msvc_Inventario.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-Producto", url = "http://localhost:8081/api/v1/producto")
public interface ProductoClientRest {

    @GetMapping("/{id}")
    Producto findProductoById(@PathVariable Long id);

    @GetMapping("/idByNombreProducto/{nombreProducto}")
    Producto getIdByNombreProducto(@PathVariable String nombreProducto);

}
