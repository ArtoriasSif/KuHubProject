package Recetas.msvc_Recetas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcRecetasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcRecetasApplication.class, args);
	}

}
