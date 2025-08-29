package Usuario.Msvc_Usuario.clients;

import Usuario.Msvc_Usuario.models.Seccion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-Rol", url = "http://localhost:8091/api/v1/rol")
public interface SeccionClientRest {

    @GetMapping("/{id}")
    ResponseEntity<Seccion> findByIdSeccion(@PathVariable Long id);

    @GetMapping("/existeSeccion/{idSeccion}")
    Boolean existeSeccionById(@PathVariable Long idSeccion);
}
