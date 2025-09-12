package Usuario.Msvc_Usuario.services;

import GlobalServerPorts.dto.ClassesModelsDtos.RolDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {

    UserDetails loadUserByUsername(String username);


}
