package msvc_Inventario.services;

import msvc_Inventario.clients.ProductoClientRest;
import msvc_Inventario.dtos.InventarioDTO;
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


    @Override
    public InventarioDTO save(InventarioDTO inventariodto) {
    return null;
    }

    @Override
    public InventarioDTO findById(Long id) {
        return null;
    }

    @Override
    public List<InventarioDTO> findAll() {
        return List.of();
    }

    @Override
    public InventarioDTO getInventarioByIdProducto(Long idProducto) {
        return null;
    }
}
