package manejoempleados;

import java.sql.*;
import java.util.*;

/**
 * Clase que contiene los metodos de SELECT, INSERT, UPDATE y DELETE para la
 * tabla de Empleados en MYSQL
 */
public class EmpleadoJDBC {
//Nos apoyamos de la llave primaria autoincrementable de MySql
    //por lo que se omite el campo de persona_id
    //Se utiliza un prepareStatement, por lo que podemos
    //utilizar parametros (signos de ?)
    //los cuales posteriormente seran sustituidos por el parametro respectivo

    private final String SQL_INSERT
            = "INSERT INTO empleados(nombre, apellido_paterno, apellido_materno, telefono) VALUES(?,?,?,?)";

    private final String SQL_UPDATE
            = "UPDATE empleados SET nombre=?, apellido_paterno=?, apellido_materno=?, telefono=? WHERE idempleados=?";

    private final String SQL_DELETE
            = "DELETE FROM empleados WHERE idempleados = ?";

    private final String SQL_SELECT
            = "SELECT idempleados, nombre, apellido_paterno, apellido_materno, telefono FROM empleados ORDER BY idempleados";

    /**
     * Metodo que inserta un registro en la tabla de Empleados
     *
     * @param nombre
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param telefono
     * @return
     */
    public int insert(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int rows = 0; //registros afectados
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;//contador de columnas
            stmt.setString(index++, nombre);//param 1 => ?
            stmt.setString(index++, apellidoPaterno);//param 2 => ?
            stmt.setString(index++, apellidoMaterno);//param 3 => ?
            stmt.setString(index++, telefono);//param 4 => ?
            System.out.println("Ejecutando query:" + SQL_INSERT);
            rows = stmt.executeUpdate();//no. registros afectados
            System.out.println("Registros afectados:" + rows);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    /**
     * Metodo que actualiza un registro existente
     *
     * @param idempleados     Es la llave primaria
     * @param nombre
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param telefono
     * @return
     */
    public int update(int idempleados, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    /**
     * Metodo que elimina un registro existente
     *
     * @param idempleados Es la llave primaria
     * @return int No. registros afectados
     */
    public int delete(int idempleados) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            System.out.println("Ejecutando query:" + SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idempleados);
            rows = stmt.executeUpdate();
            System.out.println("Registros eliminados:" + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    /**
     * Metodo que regresa el contenido de la tabla de Empleados
     */
    public List<Empleado> select() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Empleado empleado = null;
        List<Empleado> empleadoList = new ArrayList<Empleado>();
        try {
            conn = Conexion.getConnection();
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

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return empleadoList;
    }
}