package Recetas.msvc_Recetas.dtos;

import Recetas.msvc_Recetas.models.entities.Receta;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecetaResponseDTO {

    @JsonIgnore
    private Long idReceta;
    private String nombreReceta;
    private List<DetalleRecetaResponseDTO> detalleReceta;

    public RecetaResponseDTO(Receta receta, List<DetalleRecetaResponseDTO> detalles) {
    }
}
