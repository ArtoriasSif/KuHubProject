package Seccion.Msvc_Seccion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcSeccionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcSeccionApplication.class, args);
	}

}
