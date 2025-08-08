package msvc_DetalleProductoSolicitud.DetalleProducto.controllers;

import msvc_DetalleProductoSolicitud.DetalleProducto.models.entity.DetalleProductoSolicitud;
import msvc_DetalleProductoSolicitud.DetalleProducto.services.DetalleProductoSolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/detalleproductosolicitud")
@Validated
public class DetalleProductoSolicitudController {

    @Autowired
    DetalleProductoSolicitudService detalleProductoSolicitudService;


    @GetMapping("/{id}")
    public ResponseEntity<DetalleProductoSolicitud> findById(@PathVariable Long id){
        return ResponseEntity
                .status(200)
                .body(detalleProductoSolicitudService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<DetalleProductoSolicitud>> findAllDetalleProductoSolicitud(){
        return ResponseEntity
                .status(200)
                .body(detalleProductoSolicitudService.findAll());
    }

    @PostMapping
    public ResponseEntity<DetalleProductoSolicitud> saveDetalleProductoSolicitud (@Validated @RequestBody DetalleProductoSolicitud detalleProductoSolicitud){
        return ResponseEntity
                .status(201)
                .body(detalleProductoSolicitudService.saveDetalleProductoSolicitud(detalleProductoSolicitud));
    }

    //Metodo accedido por Client para verificar si existe producto vinculado al detalle
    @GetMapping("/existeProducto/{nombreProducto}")
    public Boolean existeProductoEnDetalle(@PathVariable String nombreProducto){
        return detalleProductoSolicitudService.existeProductoEnDetalle(nombreProducto);
    }

    @GetMapping("/existeProductoId/{idProducto}")
    public Boolean existeProductoIdEnDetalle(@PathVariable Long idProducto){
        return detalleProductoSolicitudService.existeProductoIdEnDetalle(idProducto);
    }


}
