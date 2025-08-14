package Usuario.Msvc_Usuario.controllers;

import Usuario.Msvc_Usuario.models.Usuario;
import Usuario.Msvc_Usuario.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//porto de la aplicacion 8084
@RestController
@RequestMapping("/api/v1/usuario")
@Validated

public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioServices;

    @GetMapping("/id/{idUsuario}")
    public ResponseEntity<Usuario> findByIdUsuario (@PathVariable Long idUsuario){
        return ResponseEntity
                .status(200)
                .body(usuarioServices.findByIdUsuario(idUsuario));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> findByUsername (@PathVariable String username){
        return ResponseEntity
                .status(200)
                .body(usuarioServices.findByUsername(username));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAllUsuarios (){
        return ResponseEntity
                .status(200)
                .body(usuarioServices.findAllUsuarios());
    }

    @PostMapping
    public ResponseEntity<Usuario> saveUsuario (@Validated @RequestBody Usuario usuario){
        return ResponseEntity
                .status(201)
                .body(usuarioServices.saveUsuario(usuario));
    }



    //PARA DELETAR USUARIO HAY QUE VALIDAR QUE ESTE NO ESTE EN UNA SOLICITUD DE UN DOCENTE E INVENTARIO
}
