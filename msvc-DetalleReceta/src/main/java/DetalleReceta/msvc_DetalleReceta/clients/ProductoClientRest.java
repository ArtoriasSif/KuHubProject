package DetalleReceta.msvc_DetalleReceta.clients;

import DetalleReceta.msvc_DetalleReceta.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-Producto", url = "http://localhost:8081/api/v1/producto")
public interface ProductoClientRest {
    @GetMapping
    ResponseEntity<List<Producto>> findAllProductos();

    @GetMapping("/id/{id}")
    ResponseEntity<Producto> findProductoById(@PathVariable Long id);
}