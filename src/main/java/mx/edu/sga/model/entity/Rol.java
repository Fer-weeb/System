package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mx.edu.sga.model.enums.NombreRol;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Rol extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 30)
    private NombreRol nombre;

    @Column(length = 150)
    private String descripcion;
}
