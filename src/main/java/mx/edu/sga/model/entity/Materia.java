package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mx.edu.sga.model.enums.TipoMateria;

@Entity
@Table(name = "materias")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Materia extends BaseEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String clave;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false)
    private Integer creditos;

    @Column(name = "horas_teoria", nullable = false)
    private Integer horasTeoria;

    @Column(name = "horas_practica", nullable = false)
    private Integer horasPractica;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMateria tipo;
}
