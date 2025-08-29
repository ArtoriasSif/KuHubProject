package Seccion.Msvc_Seccion.controllers;

import Seccion.Msvc_Seccion.dtos.SeccionNameRequestDTO;
import Seccion.Msvc_Seccion.dtos.SeccionUpdateDatesRequestDTO;
import Seccion.Msvc_Seccion.models.entity.Seccion;
import Seccion.Msvc_Seccion.services.SeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//<module>msvc-Seccion</module>	 <!-- puerto 8091 -->
@RestController
@RequestMapping("/api/v1/seccion")
@Validated
public class SeccionController {

    @Autowired
    private SeccionService seccionService;

    @GetMapping("/{id}")
    public ResponseEntity<Seccion> findByIdSeccion(@PathVariable Long id){
        return ResponseEntity
                .status(200)
                .body(seccionService.findByIdSeccion(id));
    }

    @GetMapping
    public ResponseEntity<List<Seccion>> findAllSecciones(){
        return ResponseEntity
                .status(200)
                .body(seccionService.findAllSecciones());
    }

    @PostMapping
    public ResponseEntity<Seccion> saveSeccion (@Validated @RequestBody Seccion seccion){
        return ResponseEntity
                .status(201)
                .body(seccionService.saveSeccion(seccion));
    }

    @PutMapping("/nombreporid/{idSeccion}")
    public ResponseEntity<Seccion> updateSeccionByName(
            @PathVariable Long idSeccion,
            @Validated @RequestBody SeccionNameRequestDTO request){
        return ResponseEntity
                .status(200)
                .body(seccionService.updateNameSeccionById(idSeccion, request));
    }

    @PutMapping("/nombre/{nombreSeccion}")
    public ResponseEntity<Seccion> updateSeccionByName(
            @PathVariable String nombreSeccion,
            @Validated @RequestBody SeccionNameRequestDTO request){
        return ResponseEntity
                .status(200)
                .body(seccionService.updateNameSeccionByName(nombreSeccion, request));
    }

    @PutMapping("/agregarfechas/{idSeccion}")
    public ResponseEntity<Seccion> updateSeccionAddFecha(
            @PathVariable Long idSeccion,
            @Validated @RequestBody List<SeccionUpdateDatesRequestDTO> request){
        return ResponseEntity
                .status(200)
                .body(seccionService.updateSeccionAddDates(idSeccion, request));
    }

    @PutMapping("/quitarfechas/{idSeccion}")
    public ResponseEntity<Seccion> updateSeccionRemoveDates(
            @PathVariable Long idSeccion,
            @Validated @RequestBody List<SeccionUpdateDatesRequestDTO> request){
        return ResponseEntity
                .status(200)
                .body(seccionService.updateSeccionRemoveDates(idSeccion, request));
    }

    @DeleteMapping("/{idSeccion}")
    public ResponseEntity<Void> deleteByIdSeccion(@PathVariable Long idSeccion){
        seccionService.deleteByIdSeccion(idSeccion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/existeSeccion/{idSeccion}")
    public Boolean existeSeccionById(@PathVariable Long idSeccion){
        return seccionService.existeSeccionById(idSeccion);
    }



}
