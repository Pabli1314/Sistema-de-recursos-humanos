package gm.rh.controlador;

import gm.rh.excepcion.RecursoNoEncontradoExcepcion;
import gm.rh.modelo.Empleado;
import gm.rh.servicio.EmpleadoServicio;
import gm.rh.servicio.IEmpleadoServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rh-app")
@CrossOrigin(value = "http://localhost:3000")
public class EmpleadoControlador {
    private static final Logger logger = LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados() {
        empleadoServicio.listarEmpleados().forEach((e -> logger.info(e.toString())));
        return empleadoServicio.listarEmpleados();
    }

    @PostMapping("/empleados")
    public Empleado agregarEmpleado(@RequestBody Empleado empleado) {
        logger.info("Empelado a agregar " + empleado);
        return empleadoServicio.guardarEmpleado(empleado);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Integer id) {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if (empleado == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro  el empleado con el id: " + id);
        }

        return ResponseEntity.ok(empleado);

    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer id,
            @RequestBody Empleado empleadoRecibido) {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);

        if (empleado == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe");
        }

        empleado.setNombre(empleadoRecibido.getNombre());
        empleado.setDepartamento(empleadoRecibido.getDepartamento());
        empleado.setSueldo(empleadoRecibido.getSueldo());
        empleadoServicio.guardarEmpleado(empleadoRecibido);

        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarEmpleado(@PathVariable Integer id) {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if (empleado == null) {
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        }

        empleadoServicio.eliminarEmpleado(empleado);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

}
