package msvc_Inventario.services;

import msvc_Inventario.dtos.InventarioDTO;
import msvc_Inventario.models.entities.Inventario;

import java.util.List;

public interface InventarioService {
    InventarioDTO save(InventarioDTO dto);
    InventarioDTO findById(Long id);
    List<InventarioDTO> findAll();
    Inventario getInventarioByIdProducto(Long idProducto);
    void deleteById(Long id);
    InventarioDTO update(Long id,InventarioDTO dto);
    void updateTotalInventario(Long id, float adjustmentAmount);


    //FALTA UPDATE Y DELETE


}
