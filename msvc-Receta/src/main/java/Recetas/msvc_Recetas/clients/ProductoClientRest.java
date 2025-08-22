package Recetas.msvc_Recetas.clients;


import Recetas.msvc_Recetas.models.Producto;
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


}