package Usuario.Msvc_Usuario.controllers;

import GlobalServerPorts.MicroserviciosConfig;
import Usuario.Msvc_Usuario.dtos.UpdateIdSeccionesUsuarioByAdministratorRequestDTO;
import Usuario.Msvc_Usuario.dtos.UpdateUsuarioByAdministratorRequestDTO;
import Usuario.Msvc_Usuario.dtos.UpdateUsuarioByUsuarioRequestDTO;
import Usuario.Msvc_Usuario.models.entity.Usuario;
import Usuario.Msvc_Usuario.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")  // URL fija para el controller
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private MicroserviciosConfig microserviciosConfig;

    // Endpoint de prueba para mostrar la URL centralizada
    @GetMapping("/info")
    public String mostrarUrlUsuario() {
        return "URL usuario desde config: " + microserviciosConfig.getUsuario().getUrl();
    }


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

    @PutMapping("/usuarioporadministrador/{idUsuario}")
    public ResponseEntity<Usuario> updateUsuarioByAdministradorId
        (@PathVariable Long idUsuario,
         @Validated @RequestBody UpdateUsuarioByAdministratorRequestDTO requestDTO){
        return ResponseEntity
                .status(200)
                .body(usuarioServices.updateUsuarioAdministrador(idUsuario, requestDTO));
    }

    @PutMapping("/agregarsecciones/{idUsuario}")
    public ResponseEntity<Usuario> addSeccionesToUsuarioById
        (@PathVariable Long idUsuario,
         @Validated @RequestBody UpdateIdSeccionesUsuarioByAdministratorRequestDTO requestDTO){
        return ResponseEntity
                .status(200)
                .body(usuarioServices.UpdateAddIdSeccionesUsuarioByAdministrator(idUsuario, requestDTO));
    }

    @PutMapping("/quitarsecciones/{idUsuario}")
    public ResponseEntity<Usuario> removeSeccionesToUsuarioById
         (@PathVariable Long idUsuario,
          @Validated @RequestBody UpdateIdSeccionesUsuarioByAdministratorRequestDTO requestDTO){
        return ResponseEntity
                .status(200)
                .body(usuarioServices.UpdateRemoveIdSeccionesUsuarioByAdministrator(idUsuario, requestDTO));
    }

    @PutMapping("/usuario/{idUsuario}")
    public ResponseEntity<Usuario> updateUsuarioByUsuarioWithId(
            @PathVariable Long idUsuario,
            @Validated @RequestBody UpdateUsuarioByUsuarioRequestDTO requestDTO){
        return ResponseEntity
                .status(200)
                .body(usuarioServices.UpdateUsuarioByUsuarioWithId(idUsuario, requestDTO));
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<Usuario> updateUsernameByUsername(
            @PathVariable String username,
            @Validated @RequestBody UpdateUsuarioByUsuarioRequestDTO requestDTO){
        return ResponseEntity
                .status(200)
                .body(usuarioServices.UpdateUsuarioByUsuarioWithUsername(username, requestDTO));
    }

    //Falta el Delete que hay que pensar en la ideia de poner un estado inacticvo o offline.

    //PARA DELETAR USUARIO HAY QUE VALIDAR QUE ESTE NO ESTE EN UNA SOLICITUD DE UN DOCENTE E INVENTARIO
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deleteUsuarioById(@PathVariable Long idUsuario){
        if(usuarioServices.existsByIdUsuario(idUsuario)){
            usuarioServices.deleteUsuarioById(idUsuario);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
