package gm.rh.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idEmpleado;
    String nombre;
    String departamento;
    Double sueldo;

    public Empleado orElse(Object object) {
        throw new UnsupportedOperationException("Unimplemented method 'orElse'");
    }

}
