package transacciones;

import manejoempleados.Conexion;
import manejoempleados.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene los metodos de SELECT, INSERT, UPDATE y DELETE para la
 * tabla de Empleados en MYSQL
 */

@SuppressWarnings("all")
public class EmpleadoJDBC {
    //Variable que almacena una conexion como referencia
    //esta opcion se recibe en el constructor de esta clase
    //y permite reutilizar la misma conexion para ejecutar
    //varios queries de esta clase, opcionalmente se puede
    //utilizar para el uso de una transaccion en SQL
    private java.sql.Connection userConn;

    //Cadena con el SQL de insercion
    //Nos apoyamos de la llave primaria autoincrementable de MySql
    //por lo que se omite el campo de idempleados
    //Se utiliza un prepareStatement, por lo que podemos
    //utilizar parametros (signos de ?)
    private final String SQL_INSERT
            = "INSERT INTO empleados(nombre, apellido_paterno, apellido_materno, telefono) VALUES(?,?,?,?)";

    private final String SQL_UPDATE
            = "UPDATE empleados SET nombre=?, apellido_paterno=?, apellido_materno=?, telefono=? WHERE idempleados=?";

    private final String SQL_DELETE
            = "DELETE FROM empleados WHERE idempleados = ?";

    private final String SQL_SELECT
            = "SELECT idempleados, nombre, apellido_paterno, apellido_materno, telefono FROM empleados ORDER BY idempleados";

    /*
     * Agregamos el constructor vacio
     */
    public EmpleadoJDBC() {
    }

    /**
     * Constructor que asigna una conexion existente para ser utilizada en los
     * queries de esta clase
     *
     * @param conn Conexion a la BD previamente creada
     */
    public EmpleadoJDBC(Connection conn) {
        this.userConn = conn;
    }

    /**
     * Metodo que inserta un registro en la tabla de Persona
     *
     * @throws SQLException Propagamos el error a la clase de prueba
     */
    public int insert(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;//no se utiliza en este ejercicio
        int rows = 0; //registros afectados

        try {
            //Si la conexion a reutilizar es distinto de nulo se utiliza, sino se
            //crea una nueva conexion
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;//contador de columnas
            stmt.setString(index++, nombre);//param 1 => ?
            stmt.setString(index++, apellidoPaterno);//param 2 => ?
            stmt.setString(index++, apellidoMaterno);//param 3 => ?
            stmt.setString(index++, telefono);//param 4 => ?
            System.out.println("Ejecutando query:" + SQL_INSERT);
            rows = stmt.executeUpdate();//no. registros afectados
            System.out.println("Registros afectados:" + rows);
        } finally {
            Conexion.close(stmt);
            //Unicamente cerramos la conexiÃ³n si fue creada en este metodo
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }

    /**
     * Metodo que actualiza un registro existente
     *
     * @param idempleados
     * @param nombre
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param telefono
     * @return
     * @throws SQLException Propagamos la excepcion
     */
    public int update(int idempleados, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            System.out.println("Ejecutando query:" + SQL_UPDATE);
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1;
            stmt.setString(index++, nombre);//param 1 => ?
            stmt.setString(index++, apellidoPaterno);//param 2 => ?
            stmt.setString(index++, apellidoMaterno);//param 3 => ?
            stmt.setString(index++, telefono);//param 4 => ?
            stmt.setInt(index, idempleados);
            rows = stmt.executeUpdate();
            System.out.println("Registros actualizados:" + rows);
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }

    public int delete(int idempleados) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            System.out.println("Ejecutando query:" + SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idempleados);
            rows = stmt.executeUpdate();
            System.out.println("Registros eliminados:" + rows);
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return rows;
    }

    /**
     * Metodo que imprime el contenido de la tabla de personas
     *
     * @return Lista de objetos Persona
     * @throws SQLException
     */
    public List<Empleado> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Empleado empleado = null;
        List<Empleado> empleadoList = new ArrayList<Empleado>();
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                empleado = new Empleado();
                empleado.setIdempleados(rs.getInt(1));
                empleado.setNombre(rs.getString(2));
                empleado.setApellidoPaterno(rs.getString(3));
                empleado.setApellidoMaterno(rs.getString(4));
                empleado.setTelefono(rs.getString(5));
                empleadoList.add(empleado);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return empleadoList;
    }
}