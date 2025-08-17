package msvc_Movimiento.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import msvc_Movimiento.model.entity.Movimiento;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {


    private Long idInventario;
    private Long idProducto;
    private String ubicacionInventario;
    private Float totalInventario;
    private Float inicialInventario;
    private Float devolucionInventario;





}
