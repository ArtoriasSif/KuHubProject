package Seccion.Msvc_Seccion.init;

import Seccion.Msvc_Seccion.models.entity.Seccion;
import Seccion.Msvc_Seccion.services.SeccionService;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private SeccionService seccionService;

    private List<LocalDateTime> generarFechasEjemplo() {
        List<LocalDateTime> fechas = new ArrayList<>();
        LocalDateTime inicio = LocalDateTime.now().plusDays(1).withHour(8).withMinute(30);
        for (int i = 0; i < 3; i++) {
            fechas.add(inicio.plusWeeks(i));
        }
        return fechas;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("es", "ES"));

        if (seccionService.count() == 0) {
            Set<String> nombresGenerados = new HashSet<>();

            for (int i = 1; i <= 10; i++) { // Crear 10 secciones de ejemplo
                String nombre = "Seccion " + (char) ('A' + i - 1);

                if (nombresGenerados.contains(nombre)) continue;

                Seccion seccion = new Seccion();
                seccion.setNombreSeccion(nombre);
                seccion.setIdAsignatura((long) i); // Suponiendo que ya existen asignaturas con id 1..10
                seccion.setPeriodo(faker.options().option("Diurno", "Vespertino"));
                seccion.setFechas(generarFechasEjemplo());

                seccion = seccionService.saveSeccion(seccion);
                log.info("Seccion creada: {}", seccion);

                nombresGenerados.add(nombre);
            }

            log.info("✅ Se crearon {} secciones de prueba", nombresGenerados.size());
        } else {
            log.info("⚠ Ya existen secciones, no se insertó ninguna nueva");
        }
    }
}
