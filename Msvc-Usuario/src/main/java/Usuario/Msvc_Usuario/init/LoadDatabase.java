package Usuario.Msvc_Usuario.init;

import Usuario.Msvc_Usuario.clients.RolClientRest;
import Usuario.Msvc_Usuario.models.Rol;
import Usuario.Msvc_Usuario.models.entity.Usuario;
import Usuario.Msvc_Usuario.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.Locale;

@Slf4j
@Component
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolClientRest rolRepository;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Faker faker = new Faker(new Locale("es", "ES"));

            ResponseEntity<Rol> docente = rolRepository.findByIdRol(1L);
            ResponseEntity<Rol> administrador = rolRepository.findByIdRol(2L);
            ResponseEntity<Rol> encargadoBodega = rolRepository.findByIdRol(3L);

            if (docente == null || administrador == null || encargadoBodega == null) {
                log.warn("⚠ No se encontraron los roles esperados. Inserta los roles primero.");
                return;
            }

            // Usuario Docente
            Usuario u1 = new Usuario();
            u1.setIdRol(docente.getBody().getIdRol());
            u1.setPrimeroNombre("Carlos");
            u1.setSegundoNombre("Andrés");
            u1.setApellidoPaterno("González");
            u1.setApellidoMaterno("Pérez");
            u1.setEmail("carlos.gonzalez@example.com");
            u1.setUsername("carlosg");
            u1.setPassword("Password1234"); // ✅ cumple regla

            // Usuario Administrador
            Usuario u2 = new Usuario();
            u2.setIdRol(administrador.getBody().getIdRol());
            u2.setPrimeroNombre("María");
            u2.setSegundoNombre("Elena");
            u2.setApellidoPaterno("Ramírez");
            u2.setApellidoMaterno("López");
            u2.setEmail("maria.ramirez@example.com");
            u2.setUsername("mramirez");
            u2.setPassword("AdminPass2024");

            // Usuario Encargado de Bodega
            Usuario u3 = new Usuario();
            u3.setIdRol(encargadoBodega.getBody().getIdRol());
            u3.setPrimeroNombre("Javier");
            u3.setSegundoNombre("Alonso");
            u3.setApellidoPaterno("Fernández");
            u3.setApellidoMaterno("Ríos");
            u3.setEmail("javier.fernandez@example.com");
            u3.setUsername("jfernandez");
            u3.setPassword("Bodega12345");

            usuarioRepository.saveAll(Arrays.asList(u1, u2, u3));

            log.info("✅ Se insertaron usuarios de ejemplo con roles asignados.");
        } else {
            log.info("⚠ Ya existen usuarios, no se insertó ninguno nuevo.");
        }
    }
}
