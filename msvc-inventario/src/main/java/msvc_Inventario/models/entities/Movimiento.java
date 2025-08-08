package msvc_Inventario.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

@Entity
@Table(name="movimiento")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Movimiento {

    public enum TipoMovimiento {
        ENTRADA,
        SALIDA,
        DEVOLUCION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_movimiento")
    @Schema(description="Codigo del Movimiento Realizado de Entrada o Salida",example="1")
    private Long idMovimiento;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inventario", nullable = false)
    private Inventario inventario;

    @NotNull
    @Column(name="fecha_movimiento")
    @Schema(description = "Fecha asociada a una entrada o Salida", example="05-mar")
    private LocalDate fechaMovimiento;

    @NotNull
    @Column(name="cantidad_movimiento")
    private float cantidadMovimiento;

    @NotNull
    @Column(name="tipo_movimiento")
    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipoMovimiento;

    //Metodo para pasar el tipo de Movimiento a Mayuscula SIEMPRE
    public static TipoMovimiento fromString(String value) {
        if (value == null) return null;
        try {
            return TipoMovimiento.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor inválido para TipoMovimiento: " + value);
        }
    }

    // Método para formatear fecha como "05-mar"
    public String getFechaFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM", new Locale("es", "ES"));
        return fechaMovimiento.format(formatter);
    }

    // Método para parsear fecha desde formato "05-mar"
    public void setFechaFromString(String fechaStr) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dd-MMM")
                .parseDefaulting(ChronoField.YEAR, Year.now().getValue())
                .toFormatter(new Locale("es", "ES"));

        this.fechaMovimiento = LocalDate.parse(fechaStr, formatter);
    }
}

