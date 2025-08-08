package Producto.msvc_producto.repositories;

import Producto.msvc_producto.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByNombreProducto(String nombreProducto);
    boolean existsByNombreProducto(String nombreProducto);
}
