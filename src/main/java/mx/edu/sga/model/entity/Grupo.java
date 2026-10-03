package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mx.edu.sga.model.enums.Turno;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupos",
        uniqueConstraints = @UniqueConstraint(name = "uk_grupo_periodo_materia_clave",
                columnNames = {"periodo_id", "materia_id", "clave"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Grupo extends BaseEntity {

    @Column(nullable = false, length = 20)
    private String clave;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    /** Puede ser null mientras el grupo no tenga docente asignado. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "periodo_id", nullable = false)
    private PeriodoEscolar periodo;

    @Column(name = "cupo_maximo", nullable = false)
    private Integer cupoMaximo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Turno turno;

    @Builder.Default
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Horario> horarios = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
    private List<Inscripcion> inscripciones = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluacion> evaluaciones = new ArrayList<>();

    public void addHorario(Horario h) {
        horarios.add(h);
        h.setGrupo(this);
    }

    public void addEvaluacion(Evaluacion e) {
        evaluaciones.add(e);
        e.setGrupo(this);
    }
}
