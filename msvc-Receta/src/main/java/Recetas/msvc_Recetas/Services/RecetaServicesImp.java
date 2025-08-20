package Recetas.msvc_Recetas.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Recetas.msvc_Recetas.repositories.RecetaRepository;

@Service
public class RecetaServicesImp implements RecetaServices{

    @Autowired
    private RecetaRepository recetaRepository;


}
