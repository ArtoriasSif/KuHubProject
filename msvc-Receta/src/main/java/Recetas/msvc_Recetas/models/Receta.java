package Recetas.msvc_Recetas.models;

import Recetas.msvc_Recetas.dtos.DetalleProductoSolicitudRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;


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
