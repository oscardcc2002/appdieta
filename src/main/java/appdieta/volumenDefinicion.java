package appdieta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class volumenDefinicion {
    ResultSet rs;
    Connection con;

    @FXML
    private Button btnDefinicion;

    @FXML
    private Button btnVolumen;

    @FXML
    private Pane titlePane;

    @FXML
    private ImageView btnMinimize;

    @FXML
    private ImageView btnClose;

    @FXML
    void seleccionarDefinicion(ActionEvent event) throws IOException {
        actualizarTipo("Definicion");
        App.setRoot("factorActividad");
    }

    @FXML
    void seleccionarVolumen(ActionEvent event) throws IOException {
        actualizarTipo("Volumen");
        App.setRoot("factorActividad");
    }

    private void actualizarTipo(String tipo) {
        try {
            PreparedStatement stmt = con
                    .prepareStatement("INSERT INTO PlanDeDieta (tipo, id_planDeDieta) VALUES (?, ?)");
            stmt.setString(1, tipo);
            stmt.setInt(2, generarNumeroAleatorio2);
            stmt.executeUpdate();

            /*
             * PreparedStatement userStmt = con.prepareStatement("SELECT * FROM Usuario");
             * ResultSet userResult = userStmt.executeQuery();
             * userResult.next();
             * String nombre_usuario2 = userResult.getString("nombre_usuario");
             * PreparedStatement updateStmt = con.prepareStatement(
             * "UPDATE Usuario SET id_planDeDieta = generarNumeroAleatorio2 WHERE nombre_usuario = nombre_usuario2"
             * );
             */

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int generarNumeroAleatorio2 = generarNumeroAleatorio();

    private int generarNumeroAleatorio() {
        Random random = new Random();
        return random.nextInt(10000); // Genera un número aleatorio entre 0 y 9999
    }

    @FXML
    void onCerrarClicked(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void onMinimizeClicked(MouseEvent event) {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void initialize() {
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'volumenDefinicion.fxml'.";
        assert btnDefinicion != null
                : "fx:id=\"btnDefinicion\" was not injected: check your FXML file 'volumenDefinicion.fxml'.";
        assert btnMinimize != null
                : "fx:id=\"btnMinimize\" was not injected: check your FXML file 'volumenDefinicion.fxml'.";
        assert btnVolumen != null
                : "fx:id=\"btnVolumen\" was not injected: check your FXML file 'volumenDefinicion.fxml'.";
        assert titlePane != null
                : "fx:id=\"titlePane\" was not injected: check your FXML file 'volumenDefinicion.fxml'.";

        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.1:3306/bdMyDieta", "root", "root");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM PlanDeDieta",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
