package mx.edu.sga.repository;

import mx.edu.sga.model.entity.PeriodoEscolar;
import mx.edu.sga.model.enums.EstatusPeriodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PeriodoEscolarRepository extends JpaRepository<PeriodoEscolar, Long> {

    Optional<PeriodoEscolar> findByClave(String clave);

    List<PeriodoEscolar> findByEstatus(EstatusPeriodo estatus);

    Optional<PeriodoEscolar> findFirstByEstatusOrderByFechaInicioDesc(EstatusPeriodo estatus);
}
