package DetalleReceta.msvc_DetalleReceta.controllers;

import DetalleReceta.msvc_DetalleReceta.dtos.DetalleRecetaIUpdateQuantityRequestDTO;
import DetalleReceta.msvc_DetalleReceta.exceptions.DetalleRecetaException;
import DetalleReceta.msvc_DetalleReceta.models.entities.DetalleReceta;
import DetalleReceta.msvc_DetalleReceta.services.DetalleRecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//server.port=8088
@RestController
@RequestMapping("/api/v1/detallereceta")
@Validated
public class DetalleRecetaController {

    @Autowired
    private DetalleRecetaService detalleRecetaService;

    @GetMapping("/{id}")
    public ResponseEntity<DetalleReceta> findById(@PathVariable Long id){
        return ResponseEntity
                .status(200)
                .body(detalleRecetaService.findByIdReceta(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<DetalleReceta>> findAllDetalleReceta(){
        return ResponseEntity
                .status(200)
                .body(detalleRecetaService.findAllRecetas());
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
    @DeleteMapping("/deletardetalles/{idReceta}")
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

    @DeleteMapping("/idDetalleReceta/{idDetalleReceta}")
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
