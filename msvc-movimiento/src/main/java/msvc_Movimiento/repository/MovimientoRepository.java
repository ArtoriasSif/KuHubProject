package msvc_Movimiento.repository;


import msvc_Movimiento.dtos.MovimientoDTO;
import msvc_Movimiento.model.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {


    List<Movimiento> findAllByIdInventario(Long idInventario);
    List<Movimiento> findAllByIdProducto(Long idProducto);
}