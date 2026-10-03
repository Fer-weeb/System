package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    Optional<Profesor> findByNumeroEmpleado(String numeroEmpleado);

    Optional<Profesor> findByUsuarioId(Long usuarioId);

    List<Profesor> findByActivoTrue();
}
