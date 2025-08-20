package DetalleReceta.msvc_DetalleReceta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcDetalleRecetaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcDetalleRecetaApplication.class, args);
	}

}
