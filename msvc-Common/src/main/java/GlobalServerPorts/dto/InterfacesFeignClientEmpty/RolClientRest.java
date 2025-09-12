package GlobalServerPorts.dto.InterfacesFeignClientEmpty;

import GlobalServerPorts.dto.ClassesModelsDtos.RolDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rol", url = "${microservicios.rol.url}")
public interface RolClientRest {
    @GetMapping("/rol/{idRol}")
    RolDTO findByIdRol(@PathVariable("idRol") Long idRol);

    @GetMapping("/rol/existe/{idRol}")
    Boolean existeRolById(@PathVariable("idRol") Long idRol);

    @GetMapping("/rol/nombre/{nombreRol}")
    RolDTO findByNombreRol(@PathVariable("nombreRol") String nombreRol);
}