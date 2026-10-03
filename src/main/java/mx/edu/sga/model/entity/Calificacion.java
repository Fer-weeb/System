package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/** Nota (escala 0-100) de una inscripción en una evaluación concreta. */
@Entity
@Table(name = "calificaciones",
        uniqueConstraints = @UniqueConstraint(name = "uk_calificacion_inscripcion_evaluacion",
                columnNames = {"inscripcion_id", "evaluacion_id"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Calificacion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inscripcion_id", nullable = false)
    private Inscripcion inscripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "evaluacion_id", nullable = false)
    private Evaluacion evaluacion;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal nota;

    @Column(length = 255)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrado_por")
    private Usuario registradoPor;
}
