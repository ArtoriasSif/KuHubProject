package msvc_Inventario.services;

import jakarta.transaction.Transactional;
import msvc_Inventario.dtos.CrearMovimientoDTO;
import msvc_Inventario.exception.InventarioException;
import msvc_Inventario.exception.MovimientoException;
import msvc_Inventario.models.entities.Inventario;
import msvc_Inventario.models.entities.Movimiento;
import msvc_Inventario.repositories.InventarioRepository;
import msvc_Inventario.repositories.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private InventarioRepository inventarioRepository;


    //La diferencia es:
    //  create() manda un INSERT, crea y guarda sin necesidad de instanciar
    //  save() manda un INSERT sino un UPDATE si ya existe el objeto, da mas flexibilidad
            //porque requiere instanciar el objeto manualmente, lo que te permite setear atributos uno a uno
    @Override
    @Transactional
    public CrearMovimientoDTO createMovimiento(CrearMovimientoDTO movimientodto) {

        Inventario inventario = this.inventarioRepository.findByIdInventario(movimientodto.getIdInventario());
        if (inventario == null) {
            throw new InventarioException("No existe el inventario");
        }
        Movimiento movimiento = new Movimiento();
        movimiento.setInventario(inventario);
        movimiento.setTipoMovimiento(movimientodto.getTipoMovimiento());
        movimiento.setCantidadMovimiento(movimientodto.getCantidadMovimiento());
        movimiento.setFechaMovimiento(movimientodto.getFechaMovimiento());

        //Aqui lo guardo realmente en el sistema
        Movimiento procesoDeGuardadoReal = this.movimientoRepository.save(movimiento);

        //retorno beio del DTO
        return cambiarDeMovimientoADTO(procesoDeGuardadoReal);

    }

    @Override
    public List<CrearMovimientoDTO> getMovimientosByInventario(Long idInventario) {
        return movimientoRepository.findByInventarioIdInventario(idInventario).stream()
                .map(this::cambiarDeMovimientoADTO)
                .collect(Collectors.toList());
    }

    @Override
    public CrearMovimientoDTO findById(Long id) {
        Movimiento movimiento = this.movimientoRepository.findById(id).orElseThrow(
                () -> new MovimientoException("No existe el Movimiento con el id: " + id));
        return cambiarDeMovimientoADTO(movimiento);
    }

    @Override
    public void deleteMovimiento(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(
                ()-> new MovimientoException("No existe el Movimiento"));
        movimientoRepository.delete(movimiento);
    }

    //METODO CUSTOM PARA TRABAJAR CON OBJETOS DEL MODEL Y PASARLOS A DTO
    private CrearMovimientoDTO cambiarDeMovimientoADTO(Movimiento movimiento) {
        CrearMovimientoDTO dto = new CrearMovimientoDTO();
        dto.setIdMovimiento(movimiento.getIdMovimiento());
        dto.setIdInventario(movimiento.getInventario().getIdInventario());
        dto.setFechaMovimiento(movimiento.getFechaMovimiento());
        dto.setCantidadMovimiento(movimiento.getCantidadMovimiento());
        dto.setTipoMovimiento(movimiento.getTipoMovimiento());
        return dto;
    }


}
