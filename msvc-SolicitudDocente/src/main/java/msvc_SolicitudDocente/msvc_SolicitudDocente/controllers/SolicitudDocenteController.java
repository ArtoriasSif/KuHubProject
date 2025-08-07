package msvc_SolicitudDocente.msvc_SolicitudDocente.controllers;

import msvc_SolicitudDocente.msvc_SolicitudDocente.dtos.SolicitudDocenteResponseDTO;
import msvc_SolicitudDocente.msvc_SolicitudDocente.models.entity.SolicitudDocente;
import msvc_SolicitudDocente.msvc_SolicitudDocente.services.SolicitudDocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/solicituddocente")
@Validated
public class SolicitudDocenteController {

    @Autowired
    private SolicitudDocenteService solicitudDocenteService;

    @GetMapping("/detalles")
    public ResponseEntity<Iterable<SolicitudDocenteResponseDTO>> findAllSolicitudDocentes(){
        return ResponseEntity
                .status(200)
                .body(solicitudDocenteService.findAllSolicitudDocentesConDetalles());
    }

    @PostMapping
    public ResponseEntity<SolicitudDocente> saveSolicitudDocente(@Validated @RequestBody SolicitudDocente solicitudDocente){
        return ResponseEntity
                .status(201)
                .body(solicitudDocenteService.saveSolicitudDocente(solicitudDocente));
    }
}
