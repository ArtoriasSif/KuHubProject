package Recetas.msvc_Recetas.controllers;

import Recetas.msvc_Recetas.Services.RecetaServices;
import Recetas.msvc_Recetas.dtos.RecetaResponseDTO;
import Recetas.msvc_Recetas.dtos.UpdateNameRecetaRequestDTO;
import Recetas.msvc_Recetas.models.entities.Receta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//porto de la aplicacion 8087
@RestController
@RequestMapping("/api/v1/receta")
@Validated
public class    RecetaController {

    @Autowired
    private RecetaServices recetaServices  ;

    //Recetas sin detalles
    @GetMapping
    public ResponseEntity<List<Receta>> findAllRecetas() {
        return ResponseEntity
                .status(200)
                .body(recetaServices.findAllRecetas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> findByIdReceta(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(recetaServices.findByIdReceta(id));
    }

    //Recetas con detalles
    @GetMapping("/detalles/{idReceta}")
    public ResponseEntity<RecetaResponseDTO> findByIdRecetasConDetalles(@PathVariable Long idReceta) {
        return ResponseEntity
                .status(200)
                .body(recetaServices.findByIdRecetasConDetalles(idReceta));
    }

    @GetMapping("/detalles")
    public ResponseEntity<List<RecetaResponseDTO>> findAllRecetasConDetalles() {
        return ResponseEntity
                .status(200)
                .body(recetaServices.findAllRecetasConDetalles());
    }


    @PostMapping
    public ResponseEntity<Receta> createReceta(@RequestBody Receta receta) {
        return  ResponseEntity
                .status(201)
                .body(recetaServices.save(receta));
    }

    @PutMapping("/{idReceta}")
    public ResponseEntity<Receta> updateNameByIdReceta
            (@PathVariable Long idReceta,
             @RequestBody UpdateNameRecetaRequestDTO request) {
        return ResponseEntity
                .status(200)
                .body(recetaServices.updateNameByIdReceta(idReceta, request));
    }

    //Deletar Receta en cascada, con todos los detalles
    @DeleteMapping("/deletarcondetalles/{idReceta}")
    public ResponseEntity<Void> deleteRecetaByIdReceta(@PathVariable Long idReceta) {
        try {
            recetaServices.deleteByIdReceta(idReceta);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @GetMapping("/nombre/{nombreReceta}")
    public ResponseEntity<Receta> findByNombreReceta(@PathVariable String nombreReceta) {
        return ResponseEntity
                .status(200)
                .body(this.recetaServices.findByNombreReceta(nombreReceta));
    }



}
