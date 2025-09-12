package GlobalServerPorts;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("consumer")
public class FeignConfig {

    private final MicroserviciosConfig microserviciosConfig;
    private final Map<String, Object> clients = new HashMap<>();

    public FeignConfig(MicroserviciosConfig microserviciosConfig) {
        this.microserviciosConfig = microserviciosConfig;
    }

    @Bean
    public Map<String, Object> feignClients() {
        Map<String, Class<?>> servicios = Map.of(
                "rol", GlobalServerPorts.dto.InterfacesFeignClientEmpty.RolClientRest.class,
                "seccion", GlobalServerPorts.dto.InterfacesFeignClientEmpty.SeccionClientRest.class
        );

        servicios.forEach((nombre, clientClass) -> {
            MicroserviciosConfig.Msvc msvc = microserviciosConfig.getMsvcByName(nombre);
            if (msvc == null || msvc.getUrl() == null || msvc.getUrl().isBlank()) {
                throw new IllegalArgumentException("URL no configurada para el microservicio: " + nombre);
            }

            Object client = Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .contract(new SpringMvcContract())  // <-- permite usar @GetMapping/@PostMapping
                    .requestInterceptor(template -> {     // <-- agrega token dinámico
                        // Obtiene el token de la petición actual
                        var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                        if (attributes != null) {
                            var request = attributes.getRequest();
                            String authHeader = request.getHeader("Authorization");
                            if (authHeader != null && !authHeader.isBlank()) {
                                template.header("Authorization", authHeader);
                            }
                        }
                    })
                    .target(clientClass, msvc.getUrl());

            clients.put(nombre + "Client", client);
        });

        return clients;
    }

    @SuppressWarnings("unchecked")
    public <T> T getClient(String nombre, Class<T> clazz) {
        return (T) clients.get(nombre + "Client");
    }
}
