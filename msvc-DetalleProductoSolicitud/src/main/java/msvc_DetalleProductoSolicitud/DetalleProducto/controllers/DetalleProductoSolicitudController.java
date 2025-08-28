package msvc_DetalleProductoSolicitud.DetalleProducto.controllers;

import msvc_DetalleProductoSolicitud.DetalleProducto.dtos.DetalleProductoSolicitudUpdateQuantityRequestDTO;
import msvc_DetalleProductoSolicitud.DetalleProducto.exceptions.DetalleProductoSolicitudException;
import msvc_DetalleProductoSolicitud.DetalleProducto.models.entity.DetalleProductoSolicitud;
import msvc_DetalleProductoSolicitud.DetalleProducto.services.DetalleProductoSolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;import org.springframework.web.bind.annotation.CrossOrigin;

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


    @PutMapping("/cantidad/{id}")
    public ResponseEntity<DetalleProductoSolicitud> detalleProductoSolicitudUpdateQuantity
    (@PathVariable Long id,
    @Validated @RequestBody DetalleProductoSolicitudUpdateQuantityRequestDTO QuantityRequest){
        return ResponseEntity
                .status(200)
                .body(detalleProductoSolicitudService.detalleProductoSolicitudUpdateQuantity(id, QuantityRequest));
    }



    @DeleteMapping("/detalle/{idDetalleProductoSolicitud}")
    public ResponseEntity <?> deleteDetallesByIdDetalleProductoSolicitud (@PathVariable Long idDetalleProductoSolicitud){
        try {
            detalleProductoSolicitudService.deleteByIdDetalleP(idDetalleProductoSolicitud);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (DetalleProductoSolicitudException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + e.getMessage());
        }
    }

    @DeleteMapping("/todosdetalles/{idSolicitudDocente}")
    public ResponseEntity <?> deleteTodosDetallesByIdSolicitudDocente (@PathVariable Long idSolicitudDocente){
        try {
            detalleProductoSolicitudService.deleteAllDetalleProductoSolicitud(idSolicitudDocente);
            return ResponseEntity.noContent().build(); // 204 No Content

        } catch (DetalleProductoSolicitudException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + e.getMessage());
        }
    }


    //Métodos accedidos por Client para verificar si existe producto vinculado al detalle
    @GetMapping("/existeProducto/{nombreProducto}")
    public Boolean existeProductoEnDetalle(@PathVariable String nombreProducto){
        return detalleProductoSolicitudService.existeProductoEnDetalle(nombreProducto);
    }
    @GetMapping("/existeProductoId/{idProducto}")
    public Boolean existeProductoIdEnDetalle(@PathVariable Long idProducto){
        return detalleProductoSolicitudService.existeProductoIdEnDetalle(idProducto);
    }


}
