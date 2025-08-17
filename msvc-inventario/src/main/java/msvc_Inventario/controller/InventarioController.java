package msvc_Inventario.controller;


import msvc_Inventario.dtos.InventarioDTO;
import msvc_Inventario.models.entities.Inventario;
import msvc_Inventario.services.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Porto du Phonk 8085
@CrossOrigin(origins = "*")
@RestController
@Validated
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.inventarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> save(@Validated @RequestBody InventarioDTO inventarioDTO){
        return ResponseEntity.ok().body(this.inventarioService.save(inventarioDTO));
    }

    @GetMapping
    public ResponseEntity<List<InventarioDTO>> findAll(){
        return ResponseEntity.ok().body(this.inventarioService.findAll());
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<Inventario> getInventarioByIdProducto(@PathVariable Long id){
        return ResponseEntity.ok().body(this.inventarioService.getInventarioByIdProducto(id));
    }
}
