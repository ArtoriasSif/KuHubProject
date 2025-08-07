package Producto.msvc_producto.controllers;

import Producto.msvc_producto.models.Producto;
import Producto.msvc_producto.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/producto")
@Validated
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    //Medotos creado solamete para aceder los productos por client rest para la solicitude.

    @GetMapping
    public ResponseEntity<List<Producto>> findAllProductos(){
        return ResponseEntity
                .status(200)
                .body(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> findProductoById(@PathVariable Long id){
        return ResponseEntity
                .status(200)
                .body(productoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Producto> createProducto(@Validated @RequestBody Producto producto){
        return ResponseEntity
                .status(201)
                .body(productoService.save(producto));
    }
}
