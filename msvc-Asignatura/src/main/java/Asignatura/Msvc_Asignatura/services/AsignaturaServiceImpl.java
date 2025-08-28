package Asignatura.Msvc_Asignatura.services;

import Asignatura.Msvc_Asignatura.dtos.AsignaturaUpdateRequestDTO;
import Asignatura.Msvc_Asignatura.exceptions.AsignaturaExistenteException;
import Asignatura.Msvc_Asignatura.exceptions.AsignaturaNotFoundException;
import Asignatura.Msvc_Asignatura.models.Asignatura;
import Asignatura.Msvc_Asignatura.repositories.AsignaturaRepository;
import Asignatura.Msvc_Asignatura.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AsignaturaServiceImpl implements AsignaturaService{

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Transactional
    @Override
    public Asignatura findByIdAsignatura(Long idAsignatura){
        return asignaturaRepository.findById(idAsignatura).orElseThrow(
                () -> new AsignaturaNotFoundException(idAsignatura)
        );
    }

    @Transactional
    @Override
    public List<Asignatura> findAllAsignaturas(){
        return asignaturaRepository.findAll();
    }

    @Transactional
    @Override
    public Asignatura findByNombreAsignatura(String nombreAsignatura){
        return asignaturaRepository.findByNombreAsignatura(nombreAsignatura).orElseThrow(
                () -> new AsignaturaNotFoundException(nombreAsignatura));
    }

    @Transactional
    @Override
    public Asignatura saveAsignatura(Asignatura asignatura){

        String capitalizado = StringUtils.capitalizarPalabras(asignatura.getNombreAsignatura());

        if(asignaturaRepository.existsByNombreAsignatura(capitalizado)){
            throw new AsignaturaExistenteException(capitalizado);
        }
        asignatura.setNombreAsignatura(capitalizado);
        return asignaturaRepository.save(asignatura);
    }

    @Transactional
    @Override
    public Asignatura updateAsignaturaById(Long idAsignatura, AsignaturaUpdateRequestDTO request){
        Asignatura asignatura = asignaturaRepository.findById(idAsignatura).orElseThrow(
                () -> new AsignaturaNotFoundException(idAsignatura)
        );

        String capitalizado = StringUtils.capitalizarPalabras(request.getNombreAsignatura());

        if(asignaturaRepository.existsByNombreAsignatura(capitalizado)){
            throw new AsignaturaExistenteException(capitalizado);
        }
        asignatura.setNombreAsignatura(capitalizado);
        return asignaturaRepository.save(asignatura);

    }

    @Transactional
    @Override
    public Asignatura updateAsignaturaByName(String nombreAsignatura, AsignaturaUpdateRequestDTO request){
        Asignatura asignatura = asignaturaRepository.findByNombreAsignatura(nombreAsignatura).orElseThrow(
                () -> new AsignaturaNotFoundException(nombreAsignatura));

        String capitalizado = StringUtils.capitalizarPalabras(request.getNombreAsignatura());

        if(asignaturaRepository.existsByNombreAsignatura(capitalizado)){
            throw new AsignaturaExistenteException(capitalizado);
        }
        asignatura.setNombreAsignatura(capitalizado);
        return asignaturaRepository.save(asignatura);

    }


    //Metodo en cascada eliminar secciones. Verificar si no hay estados pendientes de solicitudes, para eliminar las secciones.
    @Transactional
    @Override
    public void deleteByIdAsignatura(Long idAsignatura){
        if(!asignaturaRepository.existsById(idAsignatura)){
            throw  new AsignaturaNotFoundException(idAsignatura);
        }
        asignaturaRepository.deleteById(idAsignatura);
    }

    @Override
    public long count() {
        return asignaturaRepository.count();
    }


}
