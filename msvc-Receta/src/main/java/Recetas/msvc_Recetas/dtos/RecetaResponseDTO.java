package Recetas.msvc_Recetas.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecetaResponseDTO {

    @JsonIgnore
    private Long idReceta;
    private String nombreReceta;
    

}
