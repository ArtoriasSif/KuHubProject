package Recetas.msvc_Recetas.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//porto de la aplicacion 8087
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/receta")
@Validated
public class RecetaController {
}
