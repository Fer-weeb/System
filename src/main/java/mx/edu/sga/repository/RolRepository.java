package mx.edu.sga.repository;

import mx.edu.sga.model.entity.Rol;
import mx.edu.sga.model.enums.NombreRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(NombreRol nombre);
}
