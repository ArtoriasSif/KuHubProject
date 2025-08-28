package Asignatura.Msvc_Asignatura.controllers;

import Asignatura.Msvc_Asignatura.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<module>msvc-Asignatura</module>	<!-- puerto 8089 -->
@RestController
@RequestMapping("/api/v1/asignatura")
@Validated
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;
}
