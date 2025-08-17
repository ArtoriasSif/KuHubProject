package msvc_Inventario.init;


import msvc_Inventario.clients.ProductoClientRest;
import msvc_Inventario.models.Producto;
import msvc_Inventario.models.entities.Inventario;
import msvc_Inventario.repositories.InventarioRepository;

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
    ProductoClientRest productoClientRest;

    @Override
    public void run(String... args) throws Exception {

        if(inventarioRepository.count() == 0) {
            for (int i = 0; i < 100; i++) {

                Long idPrueba = (long) i + 1;

                Producto producto = this.productoClientRest.findProductoById(idPrueba);
                assert producto !=null;

                Inventario inventario = new Inventario(
                        producto.getIdProducto(),  // Long idProducto
                        "E5",                     // String ubicacionInventario
                        10f,                      // Float totalInventario
                        0f,                       // Float inicialInventario
                        0f                        // Float devolucionInventario
                );
                inventarioRepository.save(inventario);

                log.info("El inventario creado es {}", inventario);

            }


        }
    }
}
