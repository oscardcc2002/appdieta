package appdieta;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javafx.collections.ObservableList;

public class nuevoRegistro {
    private int id_comidas; // Add the id_comidas attribute
    private Timestamp fecha_Y_Hora;
    private String nombre_comida;
    private String nombre_alimento;
    private int cantidad;

    public nuevoRegistro(int id_comidas, Timestamp fecha_Y_Hora, String nombre_comida, String nombre_alimento,
            int cantidad) {
        this.id_comidas = id_comidas;
        this.fecha_Y_Hora = fecha_Y_Hora;
        this.nombre_comida = nombre_comida;
        this.nombre_alimento = nombre_alimento;
        this.cantidad = cantidad;
    }

    public static void llenarRegistros(ObservableList<nuevoRegistro> listaRegistros) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.1:3306/bdMyDieta", "root", "root");
        ResultSet rs;
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM Comidas");
            stmt.executeQuery();
            rs = stmt.executeQuery();
            while (rs.next()) {
                listaRegistros.add(new nuevoRegistro(
                        rs.getInt("id_comidas"),
                        rs.getTimestamp("fecha_Y_Hora"),
                        rs.getString("nombre_comida"),
                        rs.getString("nombre_alimento"),
                        rs.getInt("cantidad")));

            }
        } catch (Exception e) {

        }
    }

    public int getId_comidas() {
        return id_comidas;
    }

    public void setId_comidas(int id_comidas) {
        this.id_comidas = id_comidas;
    }

    public Timestamp getFecha_Y_Hora() {
        return fecha_Y_Hora;
    }

    public void setFecha_Y_Hora(Timestamp fecha_Y_Hora) {
        this.fecha_Y_Hora = fecha_Y_Hora;
    }

    public String getNombre_comida() {
        return nombre_comida;
    }

    public void setNombre_comida(String nombre_comida) {
        this.nombre_comida = nombre_comida;
    }

    public String getNombre_alimento() {
        return nombre_alimento;
    }

    public void setNombre_alimento(String nombre_alimento) {
        this.nombre_alimento = nombre_alimento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
