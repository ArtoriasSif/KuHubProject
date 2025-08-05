package msvc_DetalleProductoSolicitud.DetalleProducto.controllers;

import msvc_DetalleProductoSolicitud.DetalleProducto.services.DetalleProductoSolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/detalleproductosolicitud")
@Validated
public class DetalleProductoSolicitudController {

    @Autowired
    DetalleProductoSolicitudService detalleProductoSolicitudService;


}
