package Seccion.Msvc_Seccion.services;

import Seccion.Msvc_Seccion.client.AsignaturaClientRest;
import Seccion.Msvc_Seccion.dtos.SeccionNameRequestDTO;
import Seccion.Msvc_Seccion.dtos.SeccionUpdateDatesRequestDTO;
import Seccion.Msvc_Seccion.exceptions.*;
import Seccion.Msvc_Seccion.models.Asignatura;
import Seccion.Msvc_Seccion.models.entity.Seccion;
import Seccion.Msvc_Seccion.repositories.SeccionRepository;
import Seccion.Msvc_Seccion.utils.StringUtils;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SeccionServiceImpl implements SeccionService{

    @Autowired
    private SeccionRepository seccionRepository;

    @Autowired
    private AsignaturaClientRest asignaturaClientRest;

    @Transactional
    @Override
    public Seccion findByIdSeccion(Long id){
        return seccionRepository.findById(id).orElseThrow(
                () -> new SeccionNotFoundException(id));
    }

    @Transactional
    @Override
    public List<Seccion> findAllSecciones(){
        return seccionRepository.findAll();
    }

    @Transactional
    @Override
    public Seccion saveSeccion (Seccion seccion){

        try {
            Asignatura  asignatura = asignaturaClientRest.findByIdAsignatura(seccion.getIdAsignatura()).getBody();
            if (asignatura == null) {
                throw new AsignaturaNotFoundException(seccion.getIdAsignatura()); // 404
            }
        } catch (FeignException.NotFound e) {
            throw new AsignaturaNotFoundException(seccion.getIdAsignatura()); // 404
        } catch (FeignException e) {
            // Otros errores de Feign (500, 503...)
            throw new RuntimeException(
                    "Error al comunicarse con el servicio de asignaturas: " + e.status(),
                    e
            );
        }
        String capitalizado = StringUtils.capitalizarPalabras(seccion.getNombreSeccion().trim());

        if(seccionRepository.existsByNombreSeccionAndIdAsignatura(capitalizado, seccion.getIdAsignatura())){
            throw new SeccionExistenteException(capitalizado);
        }

        if (seccion.getFechas() != null && !seccion.getFechas().isEmpty()) {
            Set<LocalDateTime> fechasUnicas = new LinkedHashSet<>(seccion.getFechas());
            seccion.setFechas(new ArrayList<>(fechasUnicas));
        }

        seccion.setNombreSeccion(capitalizado);
        return seccionRepository.save(seccion);
    }

    @Transactional
    @Override
    public Seccion updateNameSeccionByName (String nombreSeccion, SeccionNameRequestDTO request){
        Seccion seccion = seccionRepository.findByNombreSeccion(nombreSeccion).orElseThrow(
                () -> new RuntimeException("Seccion con nombre: "+nombreSeccion+" no encontrado")
        );
        Asignatura asignatura = null;
        try {
            asignatura = asignaturaClientRest.findByIdAsignatura(seccion.getIdAsignatura()).getBody();
        }catch (FeignException e){
            throw new RuntimeException("Asignatura con id: "+seccion.getIdAsignatura()+" no encontrado");
        }

        String capitalizado = StringUtils.capitalizarPalabras(request.getNombreSeccion().trim());


        if(seccionRepository.existsByNombreSeccion(capitalizado)){
            throw new SeccionExistenteException(capitalizado);
        }
        seccion.setNombreSeccion(capitalizado);
        return seccionRepository.save(seccion);
    }

    @Transactional
    @Override
    public Seccion updateNameSeccionById(Long idSeccion, SeccionNameRequestDTO request){
        Seccion seccion = seccionRepository.findById(idSeccion).orElseThrow(
                () -> new SeccionNotFoundException(idSeccion));

        try {
            Asignatura  asignatura = asignaturaClientRest.findByIdAsignatura(seccion.getIdAsignatura()).getBody();
            if (asignatura == null) {
                throw new AsignaturaNotFoundException(seccion.getIdAsignatura()); // 404
            }
        } catch (FeignException.NotFound e) {
            throw new AsignaturaNotFoundException(seccion.getIdAsignatura()); // 404
        } catch (FeignException e) {
            // Otros errores de Feign (500, 503...)
            throw new RuntimeException(
                    "Error al comunicarse con el servicio de asignaturas: " + e.status(),
                    e
            );
        }

        String capitalizado = StringUtils.capitalizarPalabras(request.getNombreSeccion().trim());

        if(seccionRepository.existsByNombreSeccion(capitalizado)){
            throw new SeccionExistenteException(capitalizado);
        }
        seccion.setNombreSeccion(capitalizado);
        return seccionRepository.save(seccion);
    }

    @Transactional
    @Override
    public Seccion updateSeccionAddDates (Long idSeccion, List<SeccionUpdateDatesRequestDTO> fechaRequest){
        Seccion seccion = seccionRepository.findById(idSeccion).orElseThrow(
                () -> new SeccionNotFoundException(idSeccion));

        if (fechaRequest == null || fechaRequest.isEmpty()) {
            throw new FechasInvalidasException();
        }
        if (seccion.getFechas() == null) {
            seccion.setFechas(new ArrayList<>());
        }

        Set<LocalDateTime> fechasExistentes = new LinkedHashSet<>(seccion.getFechas());
        for (SeccionUpdateDatesRequestDTO dto : fechaRequest) {
            if (dto.getFecha() != null) {
                fechasExistentes.add(dto.getFecha());
            }
        }

        seccion.setFechas(new ArrayList<>(fechasExistentes));
        return seccionRepository.save(seccion);
    }

    @Transactional
    @Override
    public Seccion updateSeccionRemoveDates (Long idSeccion, List<SeccionUpdateDatesRequestDTO> fechaRequest){
        Seccion seccion = seccionRepository.findById(idSeccion).orElseThrow(
                () -> new SeccionNotFoundException(idSeccion));

        if (fechaRequest == null || fechaRequest.isEmpty()) {
            throw new FechasVaciasParaEliminarException();
        }
        if (seccion.getFechas() == null || seccion.getFechas().isEmpty()) {
            return seccion;
        }

        Set<LocalDateTime> fechasExistentes = new LinkedHashSet<>(seccion.getFechas());
        for (SeccionUpdateDatesRequestDTO dto : fechaRequest) {
            if (dto.getFecha() != null) {
                fechasExistentes.remove(dto.getFecha());
            }
        }

        seccion.setFechas(new ArrayList<>(fechasExistentes));
        return seccionRepository.save(seccion);
    }

    @Transactional
    @Override
    public void deleteByIdSeccion(Long id){
        if (!seccionRepository.existsById(id)) {
            throw new SeccionNotFoundException(id);
        }
        seccionRepository.deleteById(id);
    }

    @Override
    public boolean existeSeccionById(Long id){
        return seccionRepository.existsById(id);
    }

    @Override
    public long count(){
        return seccionRepository.count();
    }

}
