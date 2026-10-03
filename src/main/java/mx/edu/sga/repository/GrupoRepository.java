package mx.edu.sga.repository;

import jakarta.persistence.LockModeType;
import mx.edu.sga.model.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    List<Grupo> findByPeriodoId(Long periodoId);

    List<Grupo> findByProfesorIdAndPeriodoId(Long profesorId, Long periodoId);

    /**
     * Bloqueo pesimista (SELECT ... FOR UPDATE) para serializar inscripciones concurrentes
     * al mismo grupo: usar dentro de la transacción que valida cupo e inserta la inscripción.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select g from Grupo g where g.id = :id")
    Optional<Grupo> findByIdForUpdate(@Param("id") Long id);

    /** Grupos del periodo que todavía tienen lugares disponibles. */
    @Query("""
            select g from Grupo g
              join fetch g.materia
            where g.periodo.id = :periodoId
              and (select count(i) from Inscripcion i
                   where i.grupo = g
                     and i.estatus = mx.edu.sga.model.enums.EstatusInscripcion.INSCRITO) < g.cupoMaximo
            order by g.materia.nombre, g.clave
            """)
    List<Grupo> findGruposConCupo(@Param("periodoId") Long periodoId);

    /** Lugares disponibles = cupo máximo - inscritos activos. */
    @Query("""
            select g.cupoMaximo - (select count(i) from Inscripcion i
                                   where i.grupo = g
                                     and i.estatus = mx.edu.sga.model.enums.EstatusInscripcion.INSCRITO)
            from Grupo g where g.id = :grupoId
            """)
    Long lugaresDisponibles(@Param("grupoId") Long grupoId);
}
