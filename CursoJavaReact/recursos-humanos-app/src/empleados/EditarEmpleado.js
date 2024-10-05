import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

export default function EditarEmpleado() {
    const urlBase = "http://localhost:8080/rh-app/empleados";
        
    let navegacion = useNavigate();
    const { id } = useParams();

    const [empleado, setEmpleado] = useState({
        nombre: '',
        departamento: '',   
        sueldo: ''
    });

    const { nombre, departamento, sueldo } = empleado;

    useEffect(() => {
        cargarEmpleado();
    }, []); // Agregado un array vacío para que useEffect se ejecute solo una vez al montar el componente

    const onInputChange = (e) => {
        setEmpleado({ ...empleado, [e.target.name]: e.target.value });
    };

    const onSubmit = async (e) => {
        e.preventDefault();
        
        await axios.put(`${urlBase}/${id}`, empleado); // Cambiado a axios.put para actualizar el empleado
        navegacion("/");

    };

    const cargarEmpleado = async () => { // Eliminado el argumento 'e' ya que no se está utilizando en la función
        try {
            const resultado = await axios.get(`${urlBase}/${id}`);
            setEmpleado(resultado.data);
        } catch (error) {
            console.error("Error al cargar el empleado:", error);
        }
    };

    return (
        <div className='container'>
            <div className='container text-center' style={{ margin: "30px" }}>
                <h3>Editar Empleado</h3>
            </div>
            <form onSubmit={onSubmit}>
                <div className="mb-3">
                    <label htmlFor="nombre" className="form-label">Nombre</label>
                    <input type="text" className="form-control" id="nombre" name='nombre' required={true} value={nombre} onChange={onInputChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="departamento" className="form-label">Departamento</label>
                    <input type="text" className="form-control" id="departamento" name='departamento' required={true} value={departamento} onChange={onInputChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="sueldo" className="form-label">Sueldo</label>
                    <input type="number" step="any" className="form-control" id="sueldo" name='sueldo' required={true} value={sueldo} onChange={onInputChange} />
                </div>
                <div className='text-center'>
                    <button type="submit" className="btn btn-warning btn-sm me-3">Editar</button>
                    <a href='/' className='btn btn-danger btn-sm'>Regresar</a>
                </div>
            </form>
        </div>
    );
}
