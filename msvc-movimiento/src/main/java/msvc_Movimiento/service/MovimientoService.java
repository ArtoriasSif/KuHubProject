package msvc_Movimiento.service;


import msvc_Movimiento.dtos.CrearMovimientoDTO;

import java.util.List;

public interface MovimientoService {

    /*CrearMovimientoDTO save(CrearMovimientoDTO movimientodto);*/
    List<CrearMovimientoDTO> getMovimientosByInventario(Long idInventario);
    CrearMovimientoDTO findById(Long id);
    void deleteMovimiento(Long id);
}
