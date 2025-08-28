package Seccion.Msvc_Seccion.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SeccionUpdateDatesRequestDTO {

    private LocalDateTime fecha;
}
