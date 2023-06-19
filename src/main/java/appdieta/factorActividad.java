package appdieta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class factorActividad {

    Connection con;
    ResultSet rs;

    @FXML
    private ImageView btnClose;

    @FXML
    private Button btnHagoEjercicioADiario;

    @FXML
    private ImageView btnMinimize;

    @FXML
    private Button btnNoSoyNadaActivo;

    @FXML
    private Button btnSoyActivo;

    @FXML
    private Button btnSoyRelativamenteActivo;

    @FXML
    private Pane titlePane;

    @FXML
    void Activo(ActionEvent event) throws IOException {

        actualizarFactorActividad("Activo");
        App.setRoot("planDeDieta");

    }

    @FXML
    void EjercicioADiario(ActionEvent event) throws IOException {

        actualizarFactorActividad("Ejercicio a Diario");
        App.setRoot("planDeDieta");

    }

    @FXML
    void NadaActivo(ActionEvent event) throws IOException {

        actualizarFactorActividad("Nada Activo");
        App.setRoot("planDeDieta");

    }

    @FXML
    void RelativamenteActivo(ActionEvent event) throws IOException {

        actualizarFactorActividad("Relativamente Activo");
        App.setRoot("planDeDieta");

    }

    private void actualizarFactorActividad(String factorActividad) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.1:3306/bdMyDieta", "root", "root");
            PreparedStatement stmt = con.prepareStatement("UPDATE Usuario SET factorActividad = ?");
            stmt.setString(1, factorActividad);
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'factorActividad.fxml'.";
        assert btnHagoEjercicioADiario != null
                : "fx:id=\"btnHagoEjercicioADiario\" was not injected: check your FXML file 'factorActividad.fxml'.";
        assert btnMinimize != null
                : "fx:id=\"btnMinimize\" was not injected: check your FXML file 'factorActividad.fxml'.";
        assert btnNoSoyNadaActivo != null
                : "fx:id=\"btnNoSoyNadaActivo\" was not injected: check your FXML file 'factorActividad.fxml'.";
        assert btnSoyActivo != null
                : "fx:id=\"btnSoyActivo\" was not injected: check your FXML file 'factorActividad.fxml'.";
        assert btnSoyRelativamenteActivo != null
                : "fx:id=\"btnSoyRelativamenteActivo\" was not injected: check your FXML file 'factorActividad.fxml'.";
        assert titlePane != null : "fx:id=\"titlePane\" was not injected: check your FXML file 'factorActividad.fxml'.";

        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.1:3306/bdMyDieta", "root", "root");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Usuario", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();
            rs.first();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
