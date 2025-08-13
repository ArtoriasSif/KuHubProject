package Usuario.Msvc_Usuario.repositories;

import Usuario.Msvc_Usuario.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long>  {
}
