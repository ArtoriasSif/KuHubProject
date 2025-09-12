package Usuario.Msvc_Usuario.init;

import GlobalServerPorts.dto.ClassesModelsDtos.RolDTO;
import Usuario.Msvc_Usuario.models.entity.Usuario;
import Usuario.Msvc_Usuario.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.List;



@Slf4j
@Component
public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository; // 👈 faltaba esto

    @Autowired
    private PasswordEncoder passwordEncoder; // 👈 inyecta el encoder

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            log.info("⚠ Ya existen usuarios, no se insertó ninguno nuevo.");
            return;
        }

        RolDTO docente = new RolDTO(1L, RolDTO.RolNombreDTO.DOCENTE);
        RolDTO administrador = new RolDTO(2L, RolDTO.RolNombreDTO.ADMINISTRADOR);
        RolDTO encargadoBodega = new RolDTO(3L, RolDTO.RolNombreDTO.ENCARGADO_BODEGA);

        List<Usuario> usuarios = Arrays.asList(
                Usuario.builder()
                        .idRol(docente.getIdRol())
                        .username("carlosg")
                        .password(passwordEncoder.encode("Password1234")) // 👈 hash
                        .primeroNombre("Carlos")
                        .apellidoPaterno("González")
                        .email("carlos.gonzalez@example.com")
                        .idSecciones(List.of())
                        .build(),

                Usuario.builder()
                        .idRol(administrador.getIdRol())
                        .username("mramirez")
                        .password(passwordEncoder.encode("AdminPass2024")) // 👈 hash
                        .primeroNombre("María")
                        .apellidoPaterno("Ramírez")
                        .email("maria.ramirez@example.com")
                        .idSecciones(List.of())
                        .build(),

                Usuario.builder()
                        .idRol(encargadoBodega.getIdRol())
                        .username("jfernandez")
                        .password(passwordEncoder.encode("Bodega12345")) // 👈 hash
                        .primeroNombre("Javier")
                        .apellidoPaterno("Fernández")
                        .email("javier.fernandez@example.com")
                        .idSecciones(List.of())
                        .build()
        );

        usuarioRepository.saveAll(usuarios);
        log.info("✅ Se insertaron usuarios de ejemplo con roles asignados.");
    }

}