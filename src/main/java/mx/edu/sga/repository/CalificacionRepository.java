package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    Optional<Calificacion> findByInscripcionIdAndEvaluacionId(Long inscripcionId, Long evaluacionId);

    @Query("""
            select c from Calificacion c
              join fetch c.evaluacion e
            where c.inscripcion.id = :inscripcionId
            order by e.orden
            """)
    List<Calificacion> findByInscripcionId(@Param("inscripcionId") Long inscripcionId);

    /**
     * Calificación final ponderada = Σ(nota × porcentaje / 100).
     * Es parcial si faltan evaluaciones por capturar; null si no hay ninguna nota.
     */
    @Query("""
            select sum(c.nota * e.porcentaje / 100)
            from Calificacion c join c.evaluacion e
            where c.inscripcion.id = :inscripcionId
            """)
    BigDecimal calcularPromedioPonderado(@Param("inscripcionId") Long inscripcionId);
}
