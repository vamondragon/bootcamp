package manejoempleados;

import java.util.List;

public class ManejoEmpleados {

    public static void main(String[] args) {
        EmpleadoJDBC empleadoJDBC = new EmpleadoJDBC();
        //Prueba del metodo insert
        //empleadoJDBC.insert("Victor Alfonso", "Mondragon", "Chavez", "5535573463");

        //Prueba del metodo update
        //empleadoJDBC.update(2, "Nombre3", "Apellido3");

        //Prueba del metodo delete
        //empleadoJDBC.delete(1);

        //Prueba del metodo select
        //Uso de un objeto persona para encapsular la informacion
        //de un registro de base de datos
        List<Empleado> empleados = empleadoJDBC.select();
        for (Empleado empleado : empleados) {
            System.out.print(empleado);
            System.out.println("");
        }
    }
}
