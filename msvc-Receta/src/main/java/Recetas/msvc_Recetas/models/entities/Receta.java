package Recetas.msvc_Recetas.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity
@Table(name="receta")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @ToString
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_receta")
    private Long idReceta;

    @Column(name="nombre_receta",nullable = false,unique = true)
    @NotBlank(message = "El campo nombre receta no puede ser vacio")
    private String nombreReceta;



}
