package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/** Plan de estudios (retícula) de una carrera. */
@Entity
@Table(name = "reticulas",
        uniqueConstraints = @UniqueConstraint(name = "uk_reticula_carrera_clave", columnNames = {"carrera_id", "clave"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Reticula extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrera_id", nullable = false)
    private Carrera carrera;

    @Column(nullable = false, length = 30)
    private String clave;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(name = "anio_plan", nullable = false)
    private Integer anioPlan;

    @Builder.Default
    @Column(nullable = false)
    private Boolean vigente = true;

    @Builder.Default
    @OneToMany(mappedBy = "reticula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReticulaMateria> materias = new ArrayList<>();

    public void addMateria(ReticulaMateria rm) {
        materias.add(rm);
        rm.setReticula(this);
    }
}
