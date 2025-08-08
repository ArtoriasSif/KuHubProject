package msvc_Inventario.controller;

import jakarta.validation.Valid;
import msvc_Inventario.dtos.CrearMovimientoDTO;
import msvc_Inventario.services.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/movimiento")
@Validated
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<CrearMovimientoDTO> createMovimiento(@Valid @RequestBody CrearMovimientoDTO movimientoDTO) {
        CrearMovimientoDTO movimientoACrear = this.movimientoService.createMovimiento(movimientoDTO);
        return new ResponseEntity<>(movimientoACrear, HttpStatus.CREATED);
    }

    @GetMapping("/inventario/{idInventario}")
    public ResponseEntity<List<CrearMovimientoDTO>> getMovimientoByInventario(@PathVariable Long idInventario) {
        return ResponseEntity
                .ok(this.movimientoService.getMovimientosByInventario(idInventario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrearMovimientoDTO> findById(@PathVariable Long id) {
        return ResponseEntity
                .ok(this.movimientoService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long id) {
        this.movimientoService.deleteMovimiento(id);
        return ResponseEntity
                .noContent().build();
    }

}
