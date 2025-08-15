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
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoClientRest productoClientRest;



    //Si encuentra el producto, que le agrege la cantidad, si no lo encuentra, que lo cree.
    @Override
    public InventarioDTO save(InventarioDTO dto){
        try{
            //Checkea si existe o no el Producto en el Inventario
            Producto producto = this.productoClientRest.findProductoByName(dto.getNombreProducto());
            if (producto == null){
                //Crea Nuevo Producto
                Producto nuevoProducto = new Producto(dto.getNombreProducto(),dto.getUnidadMedida());
                //Almacena nuevo Producto
                this.productoClientRest.createProducto(nuevoProducto);
                //Prepara el objeto Inventario que se guardará en el sistema
                Inventario nuevoInventario = new Inventario(
                        nuevoProducto.getIdProducto(),
                        dto.getUbicacionInventario(),
                        dto.getTotalInventario(),
                        dto.getInicialInventario(),
                        dto.getDevolucionInventario()
                );
                this.inventarioRepository.save(nuevoInventario);
                return dto;

            }else{
                //Segun el idProducto, busca el Inventario asociado
                Inventario inventarioEncontrado = this.inventarioRepository.findByIdProducto(producto.getIdProducto()).orElseThrow(
                        () -> new InventarioException("No se encontro el producto")
                );
                //Llama al metodo que transforma el inventario a DTO de inventario
                InventarioDTO inventarioDTO = crearInventarioDTO(inventarioEncontrado);
                //Busca la cantidad en el inventario encontrado, y le agrega la cantidad
                inventarioDTO.setTotalInventario(inventarioDTO.getTotalInventario() + dto.getTotalInventario());
                return inventarioDTO;
                }




        }catch(FeignException e){
            throw new InventarioException(e.getMessage());
        }

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

        dto.setTotalInventario(inventario.getTotalInventario());
        dto.setUbicacionInventario(inventario.getUbicacionInventario());
        dto.setUnidadMedida(producto.getUnidadMedida());
        dto.setNombreProducto(producto.getNombreProducto());

        return dto;
    }
}
