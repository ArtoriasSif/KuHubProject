package Recetas.msvc_Recetas.clients;

import Recetas.msvc_Recetas.dtos.DetalleRecetaResponseDTO;
import Recetas.msvc_Recetas.models.DetalleReceta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "msvc-Receta", url = "http://localhost:8088/api/v1/detallereceta")

public interface DetalleRecetaClientRest {
    @GetMapping("/{id}")
    ResponseEntity<DetalleReceta> findById(@PathVariable Long id);

    @GetMapping("/existeReceta/{idReceta}")
    Boolean existsByIdReceta(@PathVariable("idReceta") Long idReceta);

    @DeleteMapping("/deletardetalles/{idReceta}")
    ResponseEntity<Void> deleteDetallesByReceta(@PathVariable Long idReceta);

    @GetMapping("/detalles/{idDetalleReceta}")
    ResponseEntity<DetalleRecetaResponseDTO> findAllDetalleRecetasConDetalles(@PathVariable Long idDetalleReceta);

    @GetMapping("/detalles")
    ResponseEntity<List<DetalleRecetaResponseDTO>> findAllDetalleRecetaConDetalles();

    @GetMapping("/detalles/{idDetalleReceta}")
    ResponseEntity<DetalleRecetaResponseDTO> findByIdRecetaConDetalles(@PathVariable Long idDetalleReceta);

    @GetMapping("/detalles/recetas/{idReceta}")
    ResponseEntity<List<DetalleRecetaResponseDTO>> findAllByIdRecetaConDetalles(@PathVariable Long idReceta);
}
