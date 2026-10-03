package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Alumno;
import mx.edu.sga.model.enums.EstatusAlumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Optional<Alumno> findByMatricula(String matricula);

    Optional<Alumno> findByCurp(String curp);

    boolean existsByMatricula(String matricula);

    boolean existsByCurp(String curp);

    Optional<Alumno> findByUsuarioId(Long usuarioId);

    List<Alumno> findByCarreraIdAndEstatus(Long carreraId, EstatusAlumno estatus);
}
