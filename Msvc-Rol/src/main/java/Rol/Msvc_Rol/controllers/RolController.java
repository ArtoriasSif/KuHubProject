package Rol.Msvc_Rol.controllers;

import Rol.Msvc_Rol.dtos.RolUpdateNameResquest;
import Rol.Msvc_Rol.models.Rol;
import Rol.Msvc_Rol.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rol")
@Validated
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("{idRol}")
    public ResponseEntity<Rol> findByIdRol (@PathVariable Long idRol){
        return ResponseEntity
                .status(200)
                .body(rolService.findByIdRol(idRol));
    }

    @GetMapping
    public ResponseEntity<List<Rol>> findAllRol(){
        return ResponseEntity
                .status(200)
                .body(rolService.findAllRoles());
    }

    @PostMapping
    public ResponseEntity<Rol> createRol(@Validated @RequestBody Rol rol){
        return ResponseEntity
                .status(201)
                .body(rolService.saveRol(rol));
    }

    @PutMapping("/{idRol}")
    public ResponseEntity<Rol> updateNameRolById(@PathVariable Long idRol,
                                                 @Validated @RequestBody RolUpdateNameResquest nameResquest){
        return ResponseEntity
                .status(200)
                .body(rolService.updateNameRolById(idRol, nameResquest));
    }




}
