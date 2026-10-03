package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mx.edu.sga.model.enums.DiaSemana;

import java.time.LocalTime;

@Entity
@Table(name = "horarios",
        uniqueConstraints = @UniqueConstraint(name = "uk_horario_grupo_dia_inicio",
                columnNames = {"grupo_id", "dia", "hora_inicio"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Horario extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grupo_id", nullable = false)
    private Grupo grupo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private DiaSemana dia;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(nullable = false, length = 30)
    private String aula;
}
