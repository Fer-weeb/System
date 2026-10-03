package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mx.edu.sga.model.enums.EstatusKardex;
import mx.edu.sga.model.enums.TipoAcreditacion;

import java.math.BigDecimal;

/** Historial académico definitivo: una fila por alumno-materia-periodo cerrado. */
@Entity
@Table(name = "kardex",
        uniqueConstraints = @UniqueConstraint(name = "uk_kardex_alumno_materia_periodo",
                columnNames = {"alumno_id", "materia_id", "periodo_id"}))
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Kardex extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "periodo_id", nullable = false)
    private PeriodoEscolar periodo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inscripcion_id", unique = true)
    private Inscripcion inscripcion;

    @Column(name = "calificacion_final", nullable = false, precision = 5, scale = 2)
    private BigDecimal calificacionFinal;

    @Column(name = "creditos_obtenidos", nullable = false)
    private Integer creditosObtenidos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstatusKardex estatus;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_acreditacion", nullable = false, length = 20)
    private TipoAcreditacion tipoAcreditacion = TipoAcreditacion.ORDINARIO;
}
