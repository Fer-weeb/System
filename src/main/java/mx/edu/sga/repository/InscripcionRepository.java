package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    /** Cantidad de alumnos con inscripción vigente (INSCRITO) en el grupo. */
    @Query("""
            select count(i) from Inscripcion i
            where i.grupo.id = :grupoId
              and i.estatus = mx.edu.sga.model.enums.EstatusInscripcion.INSCRITO
            """)
    long countInscritosByGrupoId(@Param("grupoId") Long grupoId);

    boolean existsByAlumnoIdAndGrupoId(Long alumnoId, Long grupoId);

    Optional<Inscripcion> findByAlumnoIdAndGrupoId(Long alumnoId, Long grupoId);

    /** ¿El alumno ya cursa esta materia (en cualquier grupo) en el periodo? */
    @Query("""
            select case when count(i) > 0 then true else false end
            from Inscripcion i
            where i.alumno.id = :alumnoId
              and i.periodo.id = :periodoId
              and i.grupo.materia.id = :materiaId
              and i.estatus = mx.edu.sga.model.enums.EstatusInscripcion.INSCRITO
            """)
    boolean existsMateriaInscritaEnPeriodo(@Param("alumnoId") Long alumnoId,
                                           @Param("periodoId") Long periodoId,
                                           @Param("materiaId") Long materiaId);

    /** Carga de un alumno en un periodo, con grupo y materia ya cargados. */
    @Query("""
            select i from Inscripcion i
              join fetch i.grupo g
              join fetch g.materia
            where i.alumno.id = :alumnoId
              and i.periodo.id = :periodoId
            order by g.materia.nombre
            """)
    List<Inscripcion> findByAlumnoAndPeriodo(@Param("alumnoId") Long alumnoId,
                                             @Param("periodoId") Long periodoId);

    /** Lista de clase de un grupo. */
    @Query("""
            select i from Inscripcion i
              join fetch i.alumno a
            where i.grupo.id = :grupoId
              and i.estatus = mx.edu.sga.model.enums.EstatusInscripcion.INSCRITO
            order by a.apellidoPaterno, a.apellidoMaterno, a.nombre
            """)
    List<Inscripcion> findListaByGrupoId(@Param("grupoId") Long grupoId);
}
