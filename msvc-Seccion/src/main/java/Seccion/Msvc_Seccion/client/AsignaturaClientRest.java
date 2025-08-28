package Seccion.Msvc_Seccion.client;


import Seccion.Msvc_Seccion.models.Asignatura;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-Asignatura", url = "http://localhost:8089/api/v1/asignatura")
public interface AsignaturaClientRest {

    @GetMapping("/{id}")
    ResponseEntity<Asignatura> findByIdAsignatura(@PathVariable Long id);


}
