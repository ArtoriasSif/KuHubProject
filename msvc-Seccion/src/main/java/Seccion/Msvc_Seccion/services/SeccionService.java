package Seccion.Msvc_Seccion.services;

import Seccion.Msvc_Seccion.dtos.SeccionNameRequestDTO;
import Seccion.Msvc_Seccion.dtos.SeccionUpdateDatesRequestDTO;
import Seccion.Msvc_Seccion.models.entity.Seccion;

import java.util.List;

public interface SeccionService {
    Seccion findByIdSeccion(Long id);
    List<Seccion> findAllSecciones();
    Seccion saveSeccion (Seccion seccion);
    Seccion updateNameSeccionById(Long idSeccion, SeccionNameRequestDTO request);
    Seccion updateNameSeccionByName (String nombreSeccion, SeccionNameRequestDTO request);
    Seccion updateSeccionAddDates (Long idSeccion, List<SeccionUpdateDatesRequestDTO> fechaRequest);
    Seccion updateSeccionRemoveDates (Long idSeccion, List<SeccionUpdateDatesRequestDTO> fechaRequest);
    void deleteByIdSeccion(Long id);

    boolean existeSeccionById(Long id);
    long count();
}
