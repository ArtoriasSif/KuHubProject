package msvc_Inventario.services;

import msvc_Inventario.dtos.CrearMovimientoDTO;

import java.util.List;

public interface MovimientoService {

    CrearMovimientoDTO createMovimiento(CrearMovimientoDTO movimientodto);
    List<CrearMovimientoDTO> getMovimientosByInventario(Long idInventario);
    CrearMovimientoDTO findById(Long id);
    void deleteMovimiento(Long id);
}
