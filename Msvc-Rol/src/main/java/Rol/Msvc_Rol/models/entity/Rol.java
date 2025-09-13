package Rol.Msvc_Rol.models.entity;

import Rol.Msvc_Rol.models.RolNombre;
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

    @Enumerated(EnumType.STRING)
    @Column(name="nombre_rol",nullable = false, unique = true, updatable = false)
    private RolNombre nombreRol;

}
