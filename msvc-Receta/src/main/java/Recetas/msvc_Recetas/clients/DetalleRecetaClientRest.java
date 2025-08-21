package Recetas.msvc_Recetas.clients;

import Recetas.msvc_Recetas.models.DetalleReceta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "msvc-Receta", url = "http://localhost:8088/api/v1/detallereceta")

public interface DetalleRecetaClientRest {
    @GetMapping("/{id}")
    ResponseEntity<DetalleReceta> findById(@PathVariable Long id);

    @GetMapping("/existeReceta/{idReceta}")
    Boolean existsByIdReceta(@PathVariable("idReceta") Long idReceta);

    @DeleteMapping("/deletardetalles/{idReceta}")
    ResponseEntity<Void> deleteDetallesByReceta(@PathVariable Long idReceta);
}
