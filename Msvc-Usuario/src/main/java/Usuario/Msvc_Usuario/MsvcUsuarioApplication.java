package Usuario.Msvc_Usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
		"Usuario.Msvc_Usuario",   // tus servicios y componentes locales
		"GlobalServerPorts"        // aquí están FeignConfig y MicroserviciosConfig
})
@EnableFeignClients(basePackages = {
		"GlobalServerPorts.dto.InterfacesFeignClientEmpty" // tus interfaces Feign
})
public class MsvcUsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcUsuarioApplication.class, args);
	}

}
