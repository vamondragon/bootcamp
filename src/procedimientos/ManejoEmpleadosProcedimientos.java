package procedimientos;

import manejoempleados.Conexion;
import manejoempleados.Empleado;
import transacciones.EmpleadoJDBC;

import java.sql.*;
import java.util.List;

public class ManejoEmpleadosProcedimientos {

    public static void main(String[] args) {

        Connection connection;

        try {
            connection = Conexion.getConnection();
            EmpleadoJDBC empleadoJDBC = new EmpleadoJDBC(connection);
            CallableStatement callableStatement = null;

            System.out.println("Insertar un nuevo empleado:");

            callableStatement = connection.prepareCall("{call insertarEmpleado(?,?,?,?)}");
            callableStatement.setString(1, "XXXXXX");
            callableStatement.setString(2, "XXXXXXX");
            callableStatement.setString(3, "XXXXXX");
            callableStatement.setString(4, "12345678910");
            callableStatement.execute();
            callableStatement.close();


            List<Empleado> empleados = empleadoJDBC.select();
            for (Empleado empleado : empleados) {
                System.out.print(empleado);
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}