package msvc_Movimiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcMovimientoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcMovimientoApplication.class, args);
    }

}
