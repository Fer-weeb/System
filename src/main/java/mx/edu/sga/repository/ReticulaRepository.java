package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Reticula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReticulaRepository extends JpaRepository<Reticula, Long> {

    Optional<Reticula> findByCarreraIdAndVigenteTrue(Long carreraId);

    Optional<Reticula> findByCarreraIdAndClave(Long carreraId, String clave);
}
