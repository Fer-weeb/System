package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mx.edu.sga.model.enums.EstatusPeriodo;

import java.time.LocalDate;

@Entity
@Table(name = "periodos_escolares")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PeriodoEscolar extends BaseEntity {

    /** Ej. "2026-1", "2026-2" */
    @Column(nullable = false, unique = true, length = 20)
    private String clave;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "fecha_inicio_inscripcion")
    private LocalDate fechaInicioInscripcion;

    @Column(name = "fecha_fin_inscripcion")
    private LocalDate fechaFinInscripcion;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstatusPeriodo estatus = EstatusPeriodo.PLANIFICACION;
}
