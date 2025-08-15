package msvc_Inventario.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name="inventario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    @Schema(description="Codigo del Inventario",example="1")
    private Long idInventario;

    @Column(name = "id_producto")
    @NotNull
    @Schema(description = "Codigo del Producto",example = "1")
    private Long idProducto;

    @Column(name = "ubicacion_inventario")
    @Schema(description = "Entrega la ubicacion del Producto en el Inventario", example = "E4")
    private String ubicacionInventario;

    @Column(name = "total_inventario")
    @Schema(description="Cantidad Total de los Productos",example="5")
    @Min(value=0, message = "El total no puede ser negativo")
    private Float totalInventario;

    @Column(name = "inicial_inventario")
    @Schema(description="Cantidad Inicial del Semestre",example="10")
    @Min(value=0, message = "El Inicial no puede ser negativo")
    private Float inicialInventario;

    @Column(name = "devolucion_inventario")
    @Schema(description="Devolucion ingresada al Inventario",example="0")
    @Min(value=0, message = "La devolucion no puede ser negativa")
    private Float devolucionInventario;

    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movimiento> movimientos;

    public Inventario(Long idProducto,String ubicacionInventario, Float totalInventario, Float inicialInventario, Float devolucionInventario) {
        this.idProducto = idProducto;
        this.ubicacionInventario = ubicacionInventario;
        this.totalInventario = totalInventario;
        this.inicialInventario = inicialInventario;
        this.devolucionInventario = devolucionInventario;

    }


}
