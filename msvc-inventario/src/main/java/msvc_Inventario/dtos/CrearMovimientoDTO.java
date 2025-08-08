package msvc_Inventario.dtos;

import lombok.*;
import msvc_Inventario.models.entities.Movimiento.TipoMovimiento;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CrearMovimientoDTO {

    private Long idMovimiento;
    private Long idInventario;
    private LocalDate fechaMovimiento; // Formato "05-mar"
    private Float cantidadMovimiento;
    private TipoMovimiento tipoMovimiento;
}
