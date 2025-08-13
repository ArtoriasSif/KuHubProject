package msvc_Inventario.services;

import feign.FeignException;
import msvc_Inventario.clients.ProductoClientRest;
import msvc_Inventario.exception.InventarioException;
import msvc_Inventario.models.Producto;
import msvc_Inventario.models.entities.Inventario;
import msvc_Inventario.repositories.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoClientRest productoClientRest;
    @Autowired
    private InventarioService inventarioService;


    @Override
    public Inventario save(Inventario inventario) {
        //Tengo que checkear que exista el producto
        //Necesito saber que usa el Frontend, idProducto o NombreProducto
        //  para mostrar la informacion en la interfaz de inventario
        try{
            Producto producto = this.productoClientRest.findProductoById(inventario.getIdProducto());
            if (producto == null) {
                throw new InventarioException("No existe el producto");
            }
        return this.inventarioRepository.save(inventario);
        }catch (FeignException ex) {
        throw new InventarioException(ex.getMessage());
        }


    }

    @Override
    public Inventario findById(Long id) {
        return this.inventarioRepository.findById(id).orElseThrow(
                () -> new InventarioException("No existe el producto"));
    }

    @Override
    public List<Inventario> findAll() {
        return this.inventarioRepository.findAll();
    }

    @Override
    public Inventario getInventarioByIdProducto(Long idProducto) {
        return this.inventarioRepository.findById(idProducto).orElseThrow(
                ()-> new InventarioException("No existe el producto"));
    }
}
