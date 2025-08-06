package msvc_DetalleProductoSolicitud.DetalleProducto.controllers;

import msvc_DetalleProductoSolicitud.DetalleProducto.models.entity.DetalleProductoSolicitud;
import msvc_DetalleProductoSolicitud.DetalleProducto.services.DetalleProductoSolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
