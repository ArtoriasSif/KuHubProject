package Rol.Msvc_Rol.init;

import Rol.Msvc_Rol.models.Rol;
import Rol.Msvc_Rol.repositories.RolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        if (rolRepository.count() == 0) {
            List<String> roles = Arrays.asList(
                    "Docente",          // ID esperado: 1
                    "Administrador",    // ID esperado: 2
                    "Encargado Bodega" // ID esperado: 3
            );

            for (String nombre : roles) {
                Rol rol = new Rol();
                rol.setNombreRol(nombre);
                rolRepository.save(rol);
                log.info("Rol creado: {}", nombre);
            }

            log.info("✅ Se crearon los roles iniciales correctamente");
        } else {
            log.info("⚠ Los roles ya existen, no se insertó ninguno nuevo");
        }
    }
}