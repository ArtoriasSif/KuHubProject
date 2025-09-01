package Producto.msvc_producto.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name="categoria")
@Getter @Setter @ToString @NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_categoria", nullable=false)
    private Long idCategoria;

    @Column(name= "nombre_categoria")
    private String nombreCategoria;

    public Categoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

}
