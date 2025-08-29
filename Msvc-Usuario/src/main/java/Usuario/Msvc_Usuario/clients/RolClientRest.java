package Usuario.Msvc_Usuario.clients;

import Usuario.Msvc_Usuario.models.Rol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Msvc-Rol", url = "http://localhost:8086/api/v1/rol")
public interface RolClientRest {

    @GetMapping("/{idRol}")
    ResponseEntity<Rol> findByIdRol (@PathVariable Long idRol);

    @GetMapping("/existe/{idRol}")
    boolean existeRolById(@PathVariable Long idRol);
}
