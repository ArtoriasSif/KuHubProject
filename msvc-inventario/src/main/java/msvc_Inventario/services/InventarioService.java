package msvc_Inventario.services;

import msvc_Inventario.dtos.InventarioDTO;

import java.util.List;

public interface InventarioService {
    InventarioDTO save(InventarioDTO inventariodto);
    InventarioDTO findById(Long id);
    List<InventarioDTO> findAll();
    InventarioDTO getInventarioByIdProducto(Long idProducto);

    //FALTA UPDATE Y DELETE


}
