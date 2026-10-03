package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KardexRepository extends JpaRepository<Kardex, Long> {

    /** Kardex completo de un alumno, ordenado cronológicamente. */
    @Query("""
            select k from Kardex k
              join fetch k.materia m
              join fetch k.periodo p
            where k.alumno.id = :alumnoId
            order by p.fechaInicio, m.nombre
            """)
    List<Kardex> findKardexByAlumnoId(@Param("alumnoId") Long alumnoId);

    /** Igual, buscando por matrícula. */
    @Query("""
            select k from Kardex k
              join fetch k.materia m
              join fetch k.periodo p
            where k.alumno.matricula = :matricula
            order by p.fechaInicio, m.nombre
            """)
    List<Kardex> findKardexByMatricula(@Param("matricula") String matricula);

    /** Promedio general sobre materias aprobadas; null si no hay registros. */
    @Query("""
            select avg(k.calificacionFinal) from Kardex k
            where k.alumno.id = :alumnoId
              and k.estatus = mx.edu.sga.model.enums.EstatusKardex.APROBADA
            """)
    Double promedioGeneral(@Param("alumnoId") Long alumnoId);

    /** Créditos acumulados (aprobados); null si no hay registros. */
    @Query("""
            select sum(k.creditosObtenidos) from Kardex k
            where k.alumno.id = :alumnoId
              and k.estatus = mx.edu.sga.model.enums.EstatusKardex.APROBADA
            """)
    Long sumCreditosAprobados(@Param("alumnoId") Long alumnoId);

    /** Ids de materias aprobadas (útil para validar prerrequisitos en memoria). */
    @Query("""
            select k.materia.id from Kardex k
            where k.alumno.id = :alumnoId
              and k.estatus = mx.edu.sga.model.enums.EstatusKardex.APROBADA
            """)
    List<Long> findMateriaIdsAprobadas(@Param("alumnoId") Long alumnoId);

    default long creditosAprobados(Long alumnoId) {
        Long total = sumCreditosAprobados(alumnoId);
        return total == null ? 0L : total;
    }
}
