package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Materia;
import mx.edu.sga.model.entity.ReticulaMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReticulaMateriaRepository extends JpaRepository<ReticulaMateria, Long> {

    Optional<ReticulaMateria> findByReticulaIdAndMateriaId(Long reticulaId, Long materiaId);

    /** Retícula completa ordenada por semestre. */
    @Query("""
            select rm from ReticulaMateria rm
              join fetch rm.materia m
            where rm.reticula.id = :reticulaId
            order by rm.semestre, m.nombre
            """)
    List<ReticulaMateria> findByReticulaOrdenada(@Param("reticulaId") Long reticulaId);

    /** Materias prerrequisito (de la retícula) que el alumno AÚN NO tiene aprobadas en su kardex. */
    @Query("""
            select p.materia from ReticulaMateria rm
              join rm.prerequisitos p
            where rm.reticula.id = :reticulaId
              and rm.materia.id = :materiaId
              and not exists (
                  select k.id from Kardex k
                  where k.alumno.id = :alumnoId
                    and k.materia.id = p.materia.id
                    and k.estatus = mx.edu.sga.model.enums.EstatusKardex.APROBADA)
            """)
    List<Materia> findPrerequisitosPendientes(@Param("reticulaId") Long reticulaId,
                                              @Param("materiaId") Long materiaId,
                                              @Param("alumnoId") Long alumnoId);

    default boolean cumplePrerequisitos(Long reticulaId, Long materiaId, Long alumnoId) {
        return findPrerequisitosPendientes(reticulaId, materiaId, alumnoId).isEmpty();
    }
}
