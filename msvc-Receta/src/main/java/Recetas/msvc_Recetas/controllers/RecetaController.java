package Recetas.msvc_Recetas.controllers;

import Recetas.msvc_Recetas.Services.RecetaServices;
import Recetas.msvc_Recetas.models.Receta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//porto de la aplicacion 8087
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/receta")
@Validated
public class RecetaController {

    @Autowired
    private RecetaServices recetaServices  ;

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

    @PostMapping
    public ResponseEntity<Receta> createReceta(@RequestBody Receta receta) {
        return  ResponseEntity
                .status(201)
                .body(recetaServices.save(receta));
    }

}
