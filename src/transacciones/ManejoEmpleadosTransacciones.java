package transacciones;

import manejoempleados.Conexion;
import transacciones.EmpleadoJDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ManejoEmpleadosTransacciones {

    public static void main(String[] args) {

        //Creamos un objeto conexion, se va a compartir
        //para todos los queries que ejecutemos
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            //Revisamos si la conexion esta en modo autocommit
            //por default es autocommit == true
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            //Creamos el objeto PersonasJDBC
            //proporcionamos la conexion creada
            EmpleadoJDBC empleadoJDBC = new EmpleadoJDBC(conn);

            //empezamos a ejecutar sentencias
            //recordar que una transaccion agrupa varias
            //sentencias SQL si algo falla no se realizan los cambios en la BD

            empleadoJDBC.update(1, "Alfonso", "Mondragon", "Chavez", "123456");

            //Provocamos un error superando los 45 caracteres del campo telefono
            empleadoJDBC.insert("Luis Fernando", "Mondragon", "Chavez",
                    "12345678910111213141516171819202122232425262728293031323334353637383940411424544454648484950");

            //guardamos los cambios
            conn.commit();

        } catch (SQLException e) {
            //Hacemos rollback en caso de error
            try {
                System.out.println("Entramos al rollback");
                //Imprimimos la excepcion a la consola
                e.printStackTrace(System.out);
                //Hacemos rollback
                conn.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace(System.out);
            }
        }
    }
}