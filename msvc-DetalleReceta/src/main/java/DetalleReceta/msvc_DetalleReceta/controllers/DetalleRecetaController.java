package DetalleReceta.msvc_DetalleReceta.controllers;

import DetalleReceta.msvc_DetalleReceta.models.DetalleReceta;
import DetalleReceta.msvc_DetalleReceta.services.DetalleRecetaService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<DetalleReceta> saveDetalleReceta (@Validated @RequestBody DetalleReceta detalleReceta){
        return ResponseEntity
                .status(201)
                .body(detalleRecetaService.saveDetalleReceta(detalleReceta));
    }

}
