package msvc_Inventario.repositories;

import msvc_Inventario.models.entities.Inventario;
import msvc_Inventario.models.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByInventario(Inventario inventario);
    List<Movimiento> findByInventarioIdInventario(Long idInventario);
}
