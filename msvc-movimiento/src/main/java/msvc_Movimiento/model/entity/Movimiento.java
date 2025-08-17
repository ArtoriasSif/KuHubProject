package msvc_Movimiento.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import msvc_Movimiento.model.Inventario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
@Table(name = "movimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {

    public enum TipoMovimiento {
        ENTRADA, SALIDA, DEVOLUCION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    @Getter @Setter
    private Long idMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inventario", nullable = false)
    private Inventario inventario;

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

    // Métodos adicionales que NO reemplazan los de Lombok
    @Transient // Indica que no es persistente
    public String getFechaFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM", new Locale("es", "ES"));
        return fechaMovimiento.format(formatter);
    }

    @Transient
    public void setFechaFromString(String fechaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM")
                .withLocale(new Locale("es", "ES"));
        this.fechaMovimiento = LocalDate.parse(fechaStr, formatter);
    }

    public static TipoMovimiento fromString(String value) {
        if (value == null) return null;
        try {
            return TipoMovimiento.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor inválido para TipoMovimiento: " + value);
        }
    }
}
