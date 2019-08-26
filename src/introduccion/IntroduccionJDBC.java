package introduccion;

import java.sql.*;

public class IntroduccionJDBC {

    public static void main(String[] args) {
        //Cadena de conexion de MySql, el parametro useSSL es opcional
        String url = "jdbc:mysql://localhost:3306/sngular_db?useSSL=false";

        // Cargamos el driver de mysql
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Creamos el objeto conexion
            Connection conexion = (Connection) DriverManager.getConnection(url, "root", "Sngular19");
            // Creamos un objeto Statement
            Statement instruccion = conexion.createStatement();
            // Creamos el query
            String sql = "SELECT idempleados, nombre, apellido_paterno, apellido_materno, telefono FROM empleados;";
            ResultSet result = instruccion.executeQuery(sql);
            while (result.next()) {
                System.out.print("Id:" + result.getInt(1));
                System.out.print(" Nombre:" + result.getString(2));
                System.out.println(" Apellido:" + result.getString(3));
            }
            // Cerrar cada uno de los objetos utilizados
            result.close();
            instruccion.close();
            conexion.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}