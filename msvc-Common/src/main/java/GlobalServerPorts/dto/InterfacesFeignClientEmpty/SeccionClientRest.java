package GlobalServerPorts.dto.InterfacesFeignClientEmpty;

import GlobalServerPorts.dto.ClassesModelsDtos.SeccionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "seccion", url = "${microservicios.seccion.url}")
public interface SeccionClientRest {

    @GetMapping("/{idSeccion}")
    SeccionDTO findById(@PathVariable("idSeccion") Long idSeccion);

    @GetMapping("/existeSeccion/{idSeccion}")
    Boolean existeSeccionById(@PathVariable("idSeccion") Long idSeccion);
}
