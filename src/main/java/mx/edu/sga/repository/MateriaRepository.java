package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Materia;
import mx.edu.sga.model.enums.TipoMateria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MateriaRepository extends JpaRepository<Materia, Long> {

    Optional<Materia> findByClave(String clave);

    List<Materia> findByTipo(TipoMateria tipo);
}
