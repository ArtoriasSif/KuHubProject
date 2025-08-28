package Asignatura.Msvc_Asignatura.controllers;

import Asignatura.Msvc_Asignatura.dtos.AsignaturaUpdateRequestDTO;
import Asignatura.Msvc_Asignatura.models.Asignatura;
import Asignatura.Msvc_Asignatura.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//<module>msvc-Asignatura</module>	<!-- puerto 8089 -->
@RestController
@RequestMapping("/api/v1/asignatura")
@Validated
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping("/{id}")
    public ResponseEntity<Asignatura> findByIdAsignatura(@PathVariable Long id){
        return ResponseEntity
                .status(200)
                .body(asignaturaService.findByIdAsignatura(id));
    }

    @GetMapping
    public ResponseEntity<List<Asignatura>> findAllAsignaturas(){
        return ResponseEntity
                .status(200)
                .body(asignaturaService.findAllAsignaturas());
    }

    @PostMapping
    public ResponseEntity<Asignatura> saveAsignatura (@Validated @RequestBody Asignatura asignatura){
        return ResponseEntity
                .status(201)
                .body(asignaturaService.saveAsignatura(asignatura));
    }

    @PutMapping("/nombre/{nombreAsignatura}")
    public ResponseEntity<Asignatura> updateAsignaturaByName(
            @PathVariable String nombreAsignatura,
            @Validated @RequestBody AsignaturaUpdateRequestDTO asignaturaUpdateRequestDTO) {
        return ResponseEntity
                .status(200)
                .body(asignaturaService.updateAsignaturaByName(nombreAsignatura, asignaturaUpdateRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asignatura> updateAsignaturaById(
            @PathVariable Long id,
            @Validated @RequestBody AsignaturaUpdateRequestDTO asignaturaUpdateRequestDTO) {
        return ResponseEntity
                .status(200)
                .body(asignaturaService.updateAsignaturaById(id, asignaturaUpdateRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByIdAsignatura(@PathVariable Long id){
        asignaturaService.deleteByIdAsignatura(id);
        return ResponseEntity.noContent().build();
    }

}
