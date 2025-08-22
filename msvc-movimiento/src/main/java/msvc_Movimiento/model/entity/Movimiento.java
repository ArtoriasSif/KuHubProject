package msvc_Movimiento.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import msvc_Movimiento.model.enums.TipoMovimiento;

import java.time.LocalDate;


@Entity
@Table(name = "movimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    @Getter @Setter
    private Long idMovimiento;

    @NotNull
    @Column
    private Long idInventario;

    @NotNull
    @Column
    private Long idProducto;

    @NotNull
    @Column(name = "fecha_movimiento")
    private LocalDate fechaMovimiento;

    @NotNull
    @Column(name = "cantidad_movimiento")
    private float cantidadMovimiento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento")
    private TipoMovimiento tipoMovimiento;

}
