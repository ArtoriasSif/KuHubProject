package msvc_Inventario.services;

import msvc_Inventario.models.entities.Inventario;

import java.util.List;

public interface InventarioService {
    Inventario save(Inventario inventario);
    Inventario findById(Long id);
    List<Inventario> findAll();
    Inventario getInventarioByIdProducto(Long idProducto);

    //FALTA UPDATE Y DELETE


}
