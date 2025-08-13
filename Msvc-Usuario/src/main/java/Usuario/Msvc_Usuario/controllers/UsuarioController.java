package Usuario.Msvc_Usuario.controllers;

import Usuario.Msvc_Usuario.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuario")
@Validated

public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioServices;
}
