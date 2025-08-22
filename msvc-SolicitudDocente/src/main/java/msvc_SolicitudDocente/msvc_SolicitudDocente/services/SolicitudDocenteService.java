package msvc_SolicitudDocente.msvc_SolicitudDocente.services;

import msvc_SolicitudDocente.msvc_SolicitudDocente.dtos.SolicitudDocenteResponseDTO;
import msvc_SolicitudDocente.msvc_SolicitudDocente.models.entity.SolicitudDocente;

import java.util.List;

public interface SolicitudDocenteService {
    SolicitudDocente saveSolicitudDocente(SolicitudDocente solicitudDocente);
    List<SolicitudDocente> findAll() ;
    List<SolicitudDocenteResponseDTO> findAllSolicitudDocentesConDetalles();
    SolicitudDocente findByIdSolicitudDocente(Long id);

}
