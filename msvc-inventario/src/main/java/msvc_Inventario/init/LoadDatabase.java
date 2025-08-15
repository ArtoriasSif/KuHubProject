package msvc_Inventario.init;


import msvc_Inventario.clients.ProductoClientRest;
import msvc_Inventario.models.Producto;
import msvc_Inventario.models.entities.Inventario;
import msvc_Inventario.repositories.InventarioRepository;
import msvc_Inventario.repositories.MovimientoRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LoadDatabase implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    MovimientoRepository movimientoRepository;

    @Autowired
    ProductoClientRest productoClientRest;

    @Override
    public void run(String... args) throws Exception {
        /*Recordar que se debe activar el Faker en el POM */

        Faker faker = new Faker(Locale.of("es","CL"));

        if(inventarioRepository.count() == 0) {
            for (int i = 0; i < 100; i++) {
                Inventario inventario = new Inventario();
                Long idPrueba = (long) i + 1;

                Producto producto = productoClientRest.findProductoById(idPrueba);
                assert producto !=null;

                Float total = 10f;

                inventario.setIdProducto(producto.getIdProducto());
                inventario.setDevolucionInventario(0f);
                inventario.setInicialInventario(0f);
                inventario.setTotalInventario(total);
                inventario.setUbicacionInventario("E5");
                inventarioRepository.save(inventario);

                log.info("El inventario creado es {}", inventario);




            }


        }
    }
}
