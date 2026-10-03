package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Parcial / criterio de evaluación de un grupo. La suma de porcentajes por grupo debe ser 100. */
@Entity
@Table(name = "evaluaciones",
        uniqueConstraints = @UniqueConstraint(name = "uk_evaluacion_grupo_orden",
                columnNames = {"grupo_id", "orden"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Evaluacion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grupo_id", nullable = false)
    private Grupo grupo;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentaje;

    @Column(nullable = false)
    private Integer orden;

    @Column(name = "fecha_aplicacion")
    private LocalDate fechaAplicacion;
}
