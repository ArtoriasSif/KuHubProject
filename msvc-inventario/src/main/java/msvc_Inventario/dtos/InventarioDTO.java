package msvc_Inventario.dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventarioDTO {

    private Long idInventario;
    private String nombreProducto;
    private String unidadMedida;
    private String ubicacionInventario;
    private Float totalInventario;
    private Float inicialInventario;
    private Float devolucionInventario;

    public InventarioDTO(String nombreProducto, String unidadMedida, String ubicacionInventario, Float totalInventario, Float inicialInventario, Float devolucionInventario) {
        this.nombreProducto = nombreProducto;
        this.unidadMedida = unidadMedida;
        this.ubicacionInventario = ubicacionInventario;
        this.totalInventario = totalInventario;
        this.inicialInventario = inicialInventario;
        this.devolucionInventario = devolucionInventario;
    }
}
