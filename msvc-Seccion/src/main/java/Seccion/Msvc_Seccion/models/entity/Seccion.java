package Seccion.Msvc_Seccion.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="seccion")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Seccion {

    @Id
    @GeneratedValue
    @Column(name="id_seccion",unique = true,nullable = false)
    private Long idSeccion;

    @Column(name="nombre_seccion",nullable = false)
    @NotNull(message = "El campo nombre no puede ser vacio")
    private String nombreSeccion;

    @Column(name="id_asignatura",nullable = false)
    @NotNull(message = "El campo id asignatura no puede ser vacio")
    private Long idAsignatura;

    @ElementCollection
    @CollectionTable(
            name = "sesion_fechas",
            joinColumns = @JoinColumn(name = "id_seccion")
    )
    @Column(name = "fecha")
    private List<LocalDateTime> fechas = new ArrayList<>();

    @Column(name="periodo",nullable = false)
    @NotBlank(message = "El campo periodo no puede ser vacio")
    private String periodo;


}
