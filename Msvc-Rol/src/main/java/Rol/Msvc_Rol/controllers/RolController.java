package Rol.Msvc_Rol.controllers;

import GlobalServerPorts.MicroserviciosConfig;
import Rol.Msvc_Rol.dtos.RolNameRequestDTO;
import Rol.Msvc_Rol.models.RolNombre;
import Rol.Msvc_Rol.models.entity.Rol;
import Rol.Msvc_Rol.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rol") // ← URL centralizada
@Validated
public class RolController {

    @Autowired
    private RolService rolService;

    @Autowired
    private MicroserviciosConfig microserviciosConfig;

    @GetMapping("/info")
    public String mostrarUrlRol() {
        return "URL rol desde config: " + microserviciosConfig.getRol().getUrl();
    }

    @GetMapping("/{idRol}")
    public ResponseEntity<Rol> findByIdRol (@PathVariable Long idRol){
        return ResponseEntity
                .status(200)
                .body(rolService.findByIdRol(idRol));
    }

    @GetMapping("/nombre/{nombreRol}")
    public ResponseEntity<Rol> findByNombreRol(@PathVariable String nombreRol){
        return ResponseEntity
                .status(200)
                .body(rolService.findByNameRol(RolNombre.valueOf(nombreRol)));
    }

    @GetMapping
    public ResponseEntity<List<Rol>> findAllRol(){
        return ResponseEntity
                .status(200)
                .body(rolService.findAllRoles());
    }

    @GetMapping("/existe/{idRol}")
    public boolean existeRolById(@PathVariable Long idRol){
        return rolService.existeRolById(idRol);
    }

    @PostMapping
    public ResponseEntity<Rol> createRol(@Validated @RequestBody RolNameRequestDTO requestDTO){
        return ResponseEntity
                .status(201)
                .body(rolService.saveRol(requestDTO));
    }

    @PutMapping("/{idRol}")
    public ResponseEntity<Rol> updateNameRolById
            (@PathVariable Long idRol,
             @Validated @RequestBody RolNameRequestDTO nameResquest){
        return ResponseEntity
                .status(200)
                .body(rolService.updateNameRolById(idRol, nameResquest));
    }

    @PutMapping("/nombre/{nombreRol}")
    public ResponseEntity<Rol> updateNameRolByName
            (@PathVariable String nombreRol,
             @Validated @RequestBody RolNameRequestDTO nameResquest){
        return ResponseEntity
                .status(200)
                .body(rolService.updateNameRolByName(nombreRol, nameResquest));
    }

    @DeleteMapping("/{idRol}")
    public ResponseEntity<Void> deleteByIdRol(@PathVariable Long idRol){
        rolService.deleteByIdRol(idRol);
        return ResponseEntity.noContent().build();
    }




}
