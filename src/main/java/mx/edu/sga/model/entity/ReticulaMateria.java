package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Materia dentro de una retícula, ubicada en un semestre.
 * Los prerrequisitos son otras ReticulaMateria de la misma retícula (auto-relación N:M).
 */
@Entity
@Table(name = "reticula_materias",
        uniqueConstraints = @UniqueConstraint(name = "uk_reticula_materia", columnNames = {"reticula_id", "materia_id"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ReticulaMateria extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reticula_id", nullable = false)
    private Reticula reticula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @Column(nullable = false)
    private Integer semestre;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reticula_materia_prerequisitos",
            joinColumns = @JoinColumn(name = "reticula_materia_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisito_id"))
    private Set<ReticulaMateria> prerequisitos = new HashSet<>();
}
