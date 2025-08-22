package Recetas.msvc_Recetas.init;

import Recetas.msvc_Recetas.models.entities.Receta;
import Recetas.msvc_Recetas.repositories.RecetaRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
public class LoadDatabase implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private RecetaRepository recetaRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("es", "ES"));

        if (recetaRepository.count() == 0) {
            Set<String> nombresGenerados = new HashSet<>();

            for (int i = 0; i < 20; ) {
                String nombre = faker.food().dish(); // genera nombres de comidas

                // Evita duplicados
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
