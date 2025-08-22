package DetalleReceta.msvc_DetalleReceta.controllers;

import DetalleReceta.msvc_DetalleReceta.dtos.DetalleRecetaIUpdateQuantityRequestDTO;
import DetalleReceta.msvc_DetalleReceta.dtos.DetalleRecetaResponseDTO;
import DetalleReceta.msvc_DetalleReceta.exceptions.DetalleRecetaException;
import DetalleReceta.msvc_DetalleReceta.models.entities.DetalleReceta;
import DetalleReceta.msvc_DetalleReceta.services.DetalleRecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//server.port=8088
@RestController
@RequestMapping("/api/v1/detallereceta")
@Validated
public class DetalleRecetaController {

    @Autowired
    private DetalleRecetaService detalleRecetaService;

    //Listar sin detalles
    @GetMapping("/{id}")
    public ResponseEntity<DetalleReceta> findByIdDetalleReceta(@PathVariable Long idDetalleReceta){
        return ResponseEntity
                .status(200)
                .body(detalleRecetaService.findByIdDetalleReceta(idDetalleReceta));
    }

    @GetMapping
    public ResponseEntity<List<DetalleReceta>> findAllDetalleRecetas(){
        return ResponseEntity
                .status(200)
                .body(detalleRecetaService.findAllDetalleRecetas());
    }


    // Listar todos los detalles de receta con DTO
    @GetMapping("/detalles")
    public ResponseEntity<List<DetalleRecetaResponseDTO>> findAllDetalleRecetasConDetalles() {
        return ResponseEntity
                .status(200)
                .body(detalleRecetaService.findAllDetalleRecetasConDetalles());
    }

    @GetMapping("/detalles/recetas/{idReceta}")
    public ResponseEntity<List<DetalleRecetaResponseDTO>> findAllByIdRecetaConDetalles(@PathVariable Long idReceta) {
        return ResponseEntity
                .status(200)
                .body(detalleRecetaService.findAllByIdRecetaConDetalles(idReceta));
    }


    // Obtener un detalle específico por idDetalleReceta
    @GetMapping("/detalles/{idDetalleReceta}")
    public ResponseEntity<DetalleRecetaResponseDTO> findByIdRecetaConDetalles(@PathVariable Long idDetalleReceta) {
        return ResponseEntity
                .status(200)
                .body(detalleRecetaService.findByIdRecetasConDetalles(idDetalleReceta));
    }


    // Metodo acedido por client para verificar si existe una receta
    @GetMapping("/existeReceta/{idReceta}")
    public ResponseEntity<Boolean> existsByIdReceta(@PathVariable Long idReceta) {
        boolean existe = detalleRecetaService.existsByIdReceta(idReceta);
        return ResponseEntity.ok(existe);
    }

    @PostMapping
    public ResponseEntity<DetalleReceta> saveDetalleReceta (@Validated @RequestBody DetalleReceta detalleReceta){
        return ResponseEntity
                .status(201)
                .body(detalleRecetaService.saveDetalleReceta(detalleReceta));
    }

    @PutMapping("/cantidad/{id}")
    public ResponseEntity<DetalleReceta> detalleRecetaUpdateQuantity
    (@PathVariable Long id,
    @Validated @RequestBody DetalleRecetaIUpdateQuantityRequestDTO quantityRequest){
        return ResponseEntity
                .status(200)
                .body(detalleRecetaService.detalleRecetaUpdateQuantity(id, quantityRequest));
    }

    //Detelar en cascada, todos los detalles de una receta, funcionalidad asignada a ADM
    @DeleteMapping("/deletartodosdetalles/{idReceta}")
    public ResponseEntity<Void> deleteDetallesByIdReceta(@PathVariable Long idReceta) {
        try {
            detalleRecetaService.deletarTodoByIdReceta(idReceta);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (DetalleRecetaException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Error
        }
    }

    @DeleteMapping("/deletardetalle/{idDetalleReceta}")
    public ResponseEntity<Void> deleteDetalleRecetaById(@PathVariable Long idDetalleReceta) {
        try {
            detalleRecetaService.deleteByidDetalleReceta(idDetalleReceta);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (DetalleRecetaException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Error
        }
    }



}
