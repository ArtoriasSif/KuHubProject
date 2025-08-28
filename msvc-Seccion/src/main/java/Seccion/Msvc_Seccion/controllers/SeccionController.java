package Seccion.Msvc_Seccion.controllers;

import Seccion.Msvc_Seccion.services.SeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<module>msvc-Seccion</module>	 <!-- puerto 8091 -->
@RestController
@RequestMapping("/api/v1/seccion")
@Validated
public class SeccionController {

    @Autowired
    private SeccionService seccionService;
}
