package Rol.Msvc_Rol.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol",nullable = false)
    private Long idRol;

    @Column(name="nombre_rol",nullable = false)
    private String nombreRol;
}
