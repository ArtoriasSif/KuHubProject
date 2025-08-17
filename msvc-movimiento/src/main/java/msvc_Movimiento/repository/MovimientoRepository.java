package msvc_Movimiento.repository;


import msvc_Movimiento.model.Inventario;
import msvc_Movimiento.model.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByInventario(Inventario inventario);
    List<Movimiento> findByInventarioIdInventario(Long idInventario);
}
