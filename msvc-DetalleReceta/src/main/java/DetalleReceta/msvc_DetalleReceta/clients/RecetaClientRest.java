package DetalleReceta.msvc_DetalleReceta.clients;

import DetalleReceta.msvc_DetalleReceta.models.Receta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-Receta", url = "http://localhost:8087/api/v1/receta")

public interface RecetaClientRest {
    @GetMapping("/{id}")
    Receta findByIdReceta(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<Receta>> findAllRecetas();

    @GetMapping("/nombre/{nombreReceta}")
    Receta findByNombreReceta(@PathVariable String nombreReceta);

}
