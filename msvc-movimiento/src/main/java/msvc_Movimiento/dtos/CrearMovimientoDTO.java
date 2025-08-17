package msvc_Movimiento.dtos;

import lombok.*;
import msvc_Movimiento.model.entity.Movimiento;


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
    private Movimiento.TipoMovimiento tipoMovimiento;
}
