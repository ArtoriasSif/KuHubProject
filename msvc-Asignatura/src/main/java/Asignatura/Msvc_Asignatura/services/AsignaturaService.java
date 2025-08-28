package Asignatura.Msvc_Asignatura.services;

import Asignatura.Msvc_Asignatura.dtos.AsignaturaUpdateRequestDTO;
import Asignatura.Msvc_Asignatura.models.Asignatura;

import java.util.List;

public interface AsignaturaService {
    Asignatura findByIdAsignatura(Long idAsignatura);
    Asignatura findByNombreAsignatura(String nombreAsignatura);
    List<Asignatura> findAllAsignaturas();
    Asignatura updateAsignaturaById(Long idAsignatura, AsignaturaUpdateRequestDTO request);
    Asignatura updateAsignaturaByName(String nombreAsignatura, AsignaturaUpdateRequestDTO request);
    Asignatura saveAsignatura(Asignatura asignatura);
    void deleteByIdAsignatura(Long idAsignatura);


    long count();
}
