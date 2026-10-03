package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mx.edu.sga.model.enums.EstatusInscripcion;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inscripciones",
        uniqueConstraints = @UniqueConstraint(name = "uk_inscripcion_alumno_grupo",
                columnNames = {"alumno_id", "grupo_id"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Inscripcion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grupo_id", nullable = false)
    private Grupo grupo;

    /** Debe coincidir con grupo.periodo (validado por trigger en BD). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "periodo_id", nullable = false)
    private PeriodoEscolar periodo;

    @CreationTimestamp
    @Column(name = "fecha_inscripcion", nullable = false, updatable = false)
    private LocalDateTime fechaInscripcion;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstatusInscripcion estatus = EstatusInscripcion.INSCRITO;

    @Builder.Default
    @OneToMany(mappedBy = "inscripcion", fetch = FetchType.LAZY)
    private List<Calificacion> calificaciones = new ArrayList<>();
}
