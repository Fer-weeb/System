package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    Optional<Carrera> findByClave(String clave);

    List<Carrera> findByActivaTrueOrderByNombre();
}
