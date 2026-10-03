package mx.edu.sga.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profesores")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Profesor extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    @Column(name = "numero_empleado", nullable = false, unique = true, length = 20)
    private String numeroEmpleado;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido_paterno", nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 100)
    private String apellidoMaterno;

    @Column(length = 20)
    private String telefono;

    @Column(length = 150)
    private String especialidad;

    @Builder.Default
    @Column(nullable = false)
    private Boolean activo = true;

    @Builder.Default
    @OneToMany(mappedBy = "profesor", fetch = FetchType.LAZY)
    private List<Grupo> grupos = new ArrayList<>();
}
