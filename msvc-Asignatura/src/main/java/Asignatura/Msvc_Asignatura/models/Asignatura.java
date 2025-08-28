package Asignatura.Msvc_Asignatura.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="asignatura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "id_asignatura",nullable = false,unique = true)
    private Long idAsignatura;

    @Column(name =  "nombre_asignatura",nullable = false,unique = true)
    private String nombreAsignatura;
}
