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
@Table(name = "carreras")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Carrera extends BaseEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String clave;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(name = "duracion_semestres", nullable = false)
    private Integer duracionSemestres;

    @Column(name = "creditos_totales", nullable = false)
    private Integer creditosTotales;

    @Builder.Default
    @Column(nullable = false)
    private Boolean activa = true;

    @Builder.Default
    @OneToMany(mappedBy = "carrera", fetch = FetchType.LAZY)
    private List<Reticula> reticulas = new ArrayList<>();
}
