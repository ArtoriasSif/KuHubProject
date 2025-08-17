package msvc_Inventario.services;

import feign.FeignException;
import msvc_Inventario.clients.ProductoClientRest;
import msvc_Inventario.dtos.InventarioDTO;
import msvc_Inventario.exception.InventarioException;
import msvc_Inventario.models.Producto;
import msvc_Inventario.models.entities.Inventario;
import msvc_Inventario.repositories.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoClientRest productoClientRest;



    //Si encuentra el producto, que le agrege la cantidad, si no lo encuentra, que lo cree.

    @Override
    public InventarioDTO save(InventarioDTO dto) {
        try {
            Producto producto;
            try {
                producto = this.productoClientRest.findProductoByName(dto.getNombreProducto());
            } catch (FeignException e) {
                if (e.status() == 404) {
                    producto = null;
                } else {
                    throw new InventarioException(e.getMessage());
                }
            }

            if (producto == null) {
                // Caso 1: El producto no existe → Crear producto + inventario nuevo
                Producto nuevoProducto = new Producto(dto.getNombreProducto(), dto.getUnidadMedida());
                nuevoProducto =this.productoClientRest.createProducto(nuevoProducto);

                Inventario nuevoInventario = new Inventario(
                        nuevoProducto.getIdProducto(),
                        dto.getUbicacionInventario(),
                        dto.getTotalInventario(),
                        dto.getInicialInventario(),
                        dto.getDevolucionInventario()
                );
                nuevoInventario= this.inventarioRepository.save(nuevoInventario);
                dto.setIdInventario(nuevoInventario.getIdInventario());
                return dto;
            } else {
                // Caso 2: El producto SÍ existe → Verificar si hay inventario
                Optional<Inventario> inventarioOpt = this.inventarioRepository.findByIdProducto(producto.getIdProducto());

                if (inventarioOpt.isPresent()) {
                    // Caso 2A: Hay inventario → Actualizar cantidad
                    Inventario inventarioEncontrado = inventarioOpt.get();
                    InventarioDTO inventarioDTO = crearInventarioDTO(inventarioEncontrado);
                    inventarioDTO.setTotalInventario(inventarioDTO.getTotalInventario() + dto.getTotalInventario());
                    return inventarioDTO;
                } else {
                    // Caso 2B: No hay inventario → Crear uno nuevo
                    Inventario nuevoInventario = new Inventario(
                            producto.getIdProducto(),
                            dto.getUbicacionInventario(),
                            dto.getTotalInventario(),
                            dto.getInicialInventario(),
                            dto.getDevolucionInventario()
                    );
                    this.inventarioRepository.save(nuevoInventario);
                    return dto;
                }
            }
        } catch (FeignException e) {
            throw new InventarioException(e.getMessage());
        }
    }

    @Override
    public InventarioDTO findById(Long id) {
        return crearInventarioDTO(this.inventarioRepository.findById(id).orElseThrow(
                () -> new InventarioException("Nose encontró el Inventario")
        ));
    }

    @Override
    public List<InventarioDTO> findAll() {
        return this.inventarioRepository.findAll().stream()
                .map(this::crearInventarioDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Inventario getInventarioByIdProducto(Long idProducto) {
        return this.inventarioRepository.findById(idProducto).orElseThrow(
                ()-> new InventarioException("No existe el producto"));
    }

    public InventarioDTO crearInventarioDTO(Inventario inventario){
        InventarioDTO dto = new InventarioDTO();
        Producto producto = this.productoClientRest.findProductoById(inventario.getIdProducto());

        dto.setIdInventario(inventario.getIdInventario());
        dto.setTotalInventario(inventario.getTotalInventario());
        dto.setUbicacionInventario(inventario.getUbicacionInventario());
        dto.setUnidadMedida(producto.getUnidadMedida());
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setDevolucionInventario(inventario.getDevolucionInventario());
        dto.setInicialInventario(inventario.getInicialInventario());

        return dto;
    }


        /* ------------------SAVE UTILIZANDO ID---------------
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
    */

}
