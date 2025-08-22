package Recetas.msvc_Recetas.init;

import Recetas.msvc_Recetas.models.entities.Receta;
import Recetas.msvc_Recetas.repositories.RecetaRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Slf4j
@Component
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private RecetaRepository recetaRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("es", "ES"));

        if (recetaRepository.count() == 0) {
            Set<String> nombresGenerados = new HashSet<>();

            for (int i = 0; i < 20; ) {
                String nombre = faker.food().dish();
                if (nombresGenerados.contains(nombre)) continue;

                Receta receta = new Receta();
                receta.setNombreReceta(nombre);
                receta = recetaRepository.save(receta);

                log.info("Receta creada: {}", receta);

                nombresGenerados.add(nombre);
                i++;
            }

            log.info("✅ Se crearon 20 recetas de prueba");
        } else {
            log.info("⚠ Ya existen recetas, no se insertó ninguna nueva");
        }
    }
}