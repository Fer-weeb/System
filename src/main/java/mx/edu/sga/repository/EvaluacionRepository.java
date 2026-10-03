package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

    List<Evaluacion> findByGrupoIdOrderByOrden(Long grupoId);

    /** Suma de porcentajes del grupo; null si aún no tiene evaluaciones (usar porcentajeAcumulado). */
    @Query("select sum(e.porcentaje) from Evaluacion e where e.grupo.id = :grupoId")
    BigDecimal sumPorcentajeByGrupoId(@Param("grupoId") Long grupoId);

    default BigDecimal porcentajeAcumulado(Long grupoId) {
        BigDecimal suma = sumPorcentajeByGrupoId(grupoId);
        return suma == null ? BigDecimal.ZERO : suma;
    }
}
