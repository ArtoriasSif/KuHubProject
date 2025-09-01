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
            Random random = new Random();

            int totalSecciones = 200;
            int totalAsignaturas = 50;

            for (int idAsignatura = 1; idAsignatura <= totalAsignaturas; idAsignatura++) {
                String nombre = "Sección " + idAsignatura;

                Seccion seccion = new Seccion();
                seccion.setNombreSeccion(nombre);
                seccion.setIdAsignatura((long) idAsignatura);
                seccion.setPeriodo(faker.options().option("Diurno", "Vespertino"));
                seccion.setFechas(generarFechasEjemplo());

                seccionService.saveSeccion(seccion);
                nombresGenerados.add(nombre);

                log.info("Sección creada para asignatura fija: {}", seccion);
            }

            for (int i = totalAsignaturas + 1; i <= totalSecciones; i++) {
                String nombre = "Sección " + i;

                Long idAsignatura = (long) (random.nextInt(totalAsignaturas) + 1);

                Seccion seccion = new Seccion();
                seccion.setNombreSeccion(nombre);
                seccion.setIdAsignatura(idAsignatura);
                seccion.setPeriodo(faker.options().option("Diurno", "Vespertino"));
                seccion.setFechas(generarFechasEjemplo());

                seccionService.saveSeccion(seccion);
                nombresGenerados.add(nombre);

                log.info("Sección creada aleatoria: {}", seccion);
            }

            log.info("✅ Se crearon {} secciones de prueba distribuidas en {} asignaturas", nombresGenerados.size(), totalAsignaturas);
        } else {
            log.info("⚠ Ya existen secciones, no se insertó ninguna nueva");
        }
    }
}