package Seccion.Msvc_Seccion.controllers;

import Seccion.Msvc_Seccion.dtos.SeccionNameRequestDTO;
import Seccion.Msvc_Seccion.dtos.SeccionUpdateDatesRequestDTO;
import Seccion.Msvc_Seccion.models.entity.Seccion;
import Seccion.Msvc_Seccion.services.SeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','CO_ADMINISTRADOR','DOCENTE_COORDINADOR')")
    public ResponseEntity<Seccion> findByIdSeccion(@PathVariable Long id){
        return ResponseEntity
                .status(200)
                .body(seccionService.findByIdSeccion(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','CO_ADMINISTRADOR','DOCENTE_COORDINADOR')")
    public ResponseEntity<List<Seccion>> findAllSecciones(){
        return ResponseEntity
                .status(200)
                .body(seccionService.findAllSecciones());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Seccion> saveSeccion(@Validated @RequestBody Seccion seccion){
        return ResponseEntity
                .status(201)
                .body(seccionService.saveSeccion(seccion));
    }

    @PutMapping("/nombreporid/{idSeccion}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Seccion> updateNameSeccionById(
            @PathVariable Long idSeccion,
            @Validated @RequestBody SeccionNameRequestDTO request){
        return ResponseEntity
                .status(200)
                .body(seccionService.updateNameSeccionById(idSeccion, request));
    }

    @PutMapping("/nombre/{nombreSeccion}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Seccion> updateNameSeccionByName(
            @PathVariable String nombreSeccion,
            @Validated @RequestBody SeccionNameRequestDTO request){
        return ResponseEntity
                .status(200)
                .body(seccionService.updateNameSeccionByName(nombreSeccion, request));
    }

    @PutMapping("/agregarfechas/{idSeccion}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Seccion> updateSeccionAddDates(
            @PathVariable Long idSeccion,
            @Validated @RequestBody List<SeccionUpdateDatesRequestDTO> request){
        return ResponseEntity
                .status(200)
                .body(seccionService.updateSeccionAddDates(idSeccion, request));
    }

    @PutMapping("/quitarfechas/{idSeccion}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Seccion> updateSeccionRemoveDates(
            @PathVariable Long idSeccion,
            @Validated @RequestBody List<SeccionUpdateDatesRequestDTO> request){
        return ResponseEntity
                .status(200)
                .body(seccionService.updateSeccionRemoveDates(idSeccion, request));
    }

    @DeleteMapping("/{idSeccion}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> deleteByIdSeccion(@PathVariable Long idSeccion){
        seccionService.deleteByIdSeccion(idSeccion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/existeSeccion/{idSeccion}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','CO_ADMINISTRADOR','DOCENTE_COORDINADOR')")
    public Boolean existeSeccionById(@PathVariable Long idSeccion){
        return seccionService.existeSeccionById(idSeccion);
    }
}

