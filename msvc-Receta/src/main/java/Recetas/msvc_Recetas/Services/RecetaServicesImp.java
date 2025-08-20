package Recetas.msvc_Recetas.Services;

import Recetas.msvc_Recetas.exceptions.RecetaException;
import Recetas.msvc_Recetas.models.Receta;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Recetas.msvc_Recetas.repositories.RecetaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaServicesImp implements RecetaServices{

    @Autowired
    private RecetaRepository recetaRepository;

    @Transactional
    @Override
    public List<Receta> findAllRecetas() {
        return recetaRepository.findAll();
    }

    @Transactional
    @Override
    public Receta findByIdReceta(Long id) {
        return recetaRepository.findById(id).orElseThrow(
                ()-> new RecetaException("La receta no existe")
        );
    }

    @Transactional
    @Override
    public Receta save (Receta receta){

        String nombre = receta.getNombreReceta().trim();
        String capitalizado = Arrays.stream(nombre.split("\\s+"))
                .map(p -> p.substring(0, 1).toUpperCase() + p.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

        if (recetaRepository.existsByNombreReceta(capitalizado)){
            throw new RecetaException("El nombre receta ya existe");
        }

        receta.setNombreReceta(capitalizado);

        return recetaRepository.save(receta);
    }

}
