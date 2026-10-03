package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Horario;
import mx.edu.sga.model.enums.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

/**
 * Detección de choques. Dos rangos [a,b) y [c,d) se traslapan si a < d AND b > c
 * (los extremos que se tocan, ej. 10:00-11:00 y 11:00-12:00, NO cuentan como choque).
 */
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByGrupoId(Long grupoId);

    // ---------- Choque de AULA ----------
    @Query("""
            select h from Horario h
            where h.grupo.periodo.id = :periodoId
              and h.aula = :aula
              and h.dia = :dia
              and h.horaInicio < :horaFin
              and h.horaFin > :horaInicio
              and h.grupo.id <> :excluirGrupoId
            """)
    List<Horario> findChoquesAulaExcluyendoGrupo(@Param("periodoId") Long periodoId,
                                                 @Param("aula") String aula,
                                                 @Param("dia") DiaSemana dia,
                                                 @Param("horaInicio") LocalTime horaInicio,
                                                 @Param("horaFin") LocalTime horaFin,
                                                 @Param("excluirGrupoId") Long excluirGrupoId);

    /** Para un horario nuevo (grupo aún sin id). */
    default List<Horario> findChoquesAula(Long periodoId, String aula, DiaSemana dia,
                                          LocalTime horaInicio, LocalTime horaFin) {
        return findChoquesAulaExcluyendoGrupo(periodoId, aula, dia, horaInicio, horaFin, 0L);
    }

    // ---------- Choque de PROFESOR ----------
    @Query("""
            select h from Horario h
            where h.grupo.periodo.id = :periodoId
              and h.grupo.profesor.id = :profesorId
              and h.dia = :dia
              and h.horaInicio < :horaFin
              and h.horaFin > :horaInicio
              and h.grupo.id <> :excluirGrupoId
            """)
    List<Horario> findChoquesProfesorExcluyendoGrupo(@Param("periodoId") Long periodoId,
                                                     @Param("profesorId") Long profesorId,
                                                     @Param("dia") DiaSemana dia,
                                                     @Param("horaInicio") LocalTime horaInicio,
                                                     @Param("horaFin") LocalTime horaFin,
                                                     @Param("excluirGrupoId") Long excluirGrupoId);

    default List<Horario> findChoquesProfesor(Long periodoId, Long profesorId, DiaSemana dia,
                                              LocalTime horaInicio, LocalTime horaFin) {
        return findChoquesProfesorExcluyendoGrupo(periodoId, profesorId, dia, horaInicio, horaFin, 0L);
    }

    // ---------- Choque de ALUMNO ----------
    /**
     * Horarios de grupos en los que el alumno YA está inscrito (mismo periodo) que se traslapan
     * con algún horario del grupo que desea cursar. Lista vacía = sin choques.
     */
    @Query("""
            select distinct h from Inscripcion i
              join i.grupo g
              join g.horarios h
            where i.alumno.id = :alumnoId
              and g.periodo.id = :periodoId
              and g.id <> :grupoNuevoId
              and i.estatus = mx.edu.sga.model.enums.EstatusInscripcion.INSCRITO
              and exists (
                  select n.id from Horario n
                  where n.grupo.id = :grupoNuevoId
                    and n.dia = h.dia
                    and n.horaInicio < h.horaFin
                    and n.horaFin > h.horaInicio)
            """)
    List<Horario> findChoquesAlumno(@Param("alumnoId") Long alumnoId,
                                    @Param("periodoId") Long periodoId,
                                    @Param("grupoNuevoId") Long grupoNuevoId);
}
