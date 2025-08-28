package Asignatura.Msvc_Asignatura.init;

import Asignatura.Msvc_Asignatura.models.Asignatura;
import Asignatura.Msvc_Asignatura.services.AsignaturaService;
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
    private AsignaturaService asignaturaService;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("es", "ES"));

        if (asignaturaService.count() == 0) { // agregamos un método count() en el service
            Set<String> nombresGenerados = new HashSet<>();

            for (int i = 0; i < 50; ) {  // número de asignaturas a crear
                String nombre = faker.educator().course();

                if (nombresGenerados.contains(nombre)) continue;

                Asignatura asignatura = new Asignatura();
                asignatura.setNombreAsignatura(nombre);

                try {
                    asignatura = asignaturaService.saveAsignatura(asignatura); // usa tu lógica de capitalización y validación
                    log.info("Asignatura creada: {}", asignatura.getNombreAsignatura());
                    nombresGenerados.add(nombre);
                    i++;
                } catch (Exception e) {
                    log.warn("No se pudo crear la asignatura {}: {}", nombre, e.getMessage());
                }
            }

            log.info("✅ Se crearon asignaturas de prueba");
        } else {
            log.info("⚠ Ya existen asignaturas, no se insertó ninguna nueva");
        }
    }
}
