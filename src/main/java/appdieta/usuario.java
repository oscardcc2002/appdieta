package appdieta;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class usuario {
    Connection con;
    ResultSet rs;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView btnClose;

    @FXML
    private Button btnComidas;

    @FXML
    private Button btnEdtar;

    @FXML
    private Button btnGuardarCambios;

    @FXML
    private ImageView btnMinimize;

    @FXML
    private Button btnRegistros;

    @FXML
    private Button btnPlanDeDieta;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Pane titlePane;

    @FXML
    private TextField txtAltura;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtNombre_usuario;

    @FXML
    private TextField txtPeso;

    @FXML
    private RadioButton btnVolumen;

    @FXML
    private RadioButton btnDefinicion;

    @FXML
    private RadioButton btnRelativamenteActivo;

    @FXML
    private RadioButton btnNadaActivo;

    @FXML
    private RadioButton btnActivo;

    @FXML
    private RadioButton btnEjercicioADiario;

    @FXML
    void onDefinicionClicked(ActionEvent event) {
        actualizarTipo("Definicion");
    }

    @FXML
    void onVolumenClicked(ActionEvent event) {
        actualizarTipo("Volumen");

    }

    private void actualizarTipo(String tipo) {
        try {

            PreparedStatement planStmt = con.prepareStatement("SELECT * FROM PlanDeDieta ");

            ResultSet planResult = planStmt.executeQuery();
            planResult.next();
            // Guardar los valores obtenidos en variables locales
            int id_planDeDieta = planResult.getInt("id_planDeDieta");
            PreparedStatement stmt = con.prepareStatement("UPDATE PlanDeDieta SET tipo = ? WHERE id_planDeDieta = ?");
            stmt.setString(1, tipo);
            stmt.setInt(2, id_planDeDieta);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        txtPeso.setEditable(true);
        txtAltura.setEditable(true);
        txtEdad.setEditable(true);
    }

    @FXML
    void guardarCambios(ActionEvent event) {
        try {

            rs.updateString("nombre_usuario", txtNombre_usuario.getText());
            rs.updateString("edad", txtEdad.getText());
            rs.updateString("altura", txtAltura.getText());
            rs.updateString("peso", txtPeso.getText());

            rs.updateRow();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Error al acceder a la base de datos: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizarActividadFisica(String actividad) {
        try {
            rs.updateString("factorActividad", actividad);
            rs.updateRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onNadaActivoClicked(ActionEvent event) {
        actualizarActividadFisica("Nada Activo");

    }

    @FXML
    void onRelativamenteActivoClicked(ActionEvent event) {
        actualizarActividadFisica("Relativamente Activo");

    }

    @FXML
    void onEjercicioADiarioClicked(ActionEvent event) {
        actualizarActividadFisica("Ejercicio a Diario");

    }

    @FXML
    void onActivoClicked(ActionEvent event) {
        actualizarActividadFisica("Activo");

    }

    @FXML
    void switchToPlanDeDieta(ActionEvent event) throws IOException {
        App.setRoot("planDeDieta");
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
    void switchToComidas(ActionEvent event) throws IOException {
        App.setRoot("comidas");
    }

    @FXML
    void switchToRegistros(ActionEvent event) throws IOException {
        App.setRoot("registros");
    }

    @FXML
    void initialize() {
        assert btnActivo != null : "fx:id=\"btnActivo\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnComidas != null : "fx:id=\"btnComidas\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnDefinicion != null : "fx:id=\"btnDefinicion\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnEdtar != null : "fx:id=\"btnEdtar\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnEjercicioADiario != null
                : "fx:id=\"btnEjercicioADiario\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnGuardarCambios != null
                : "fx:id=\"btnGuardarCambios\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnMinimize != null : "fx:id=\"btnMinimize\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnNadaActivo != null : "fx:id=\"btnNadaActivo\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnPlanDeDieta != null
                : "fx:id=\"btnPlanDeDieta\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnRegistros != null : "fx:id=\"btnRegistros\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnRelativamenteActivo != null
                : "fx:id=\"btnRelativamenteActivo\" was not injected: check your FXML file 'usuario.fxml'.";
        assert btnVolumen != null : "fx:id=\"btnVolumen\" was not injected: check your FXML file 'usuario.fxml'.";
        assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'usuario.fxml'.";
        assert titlePane != null : "fx:id=\"titlePane\" was not injected: check your FXML file 'usuario.fxml'.";
        assert txtAltura != null : "fx:id=\"txtAltura\" was not injected: check your FXML file 'usuario.fxml'.";
        assert txtEdad != null : "fx:id=\"txtEdad\" was not injected: check your FXML file 'usuario.fxml'.";
        assert txtNombre_usuario != null
                : "fx:id=\"txtNombre_usuario\" was not injected: check your FXML file 'usuario.fxml'.";
        assert txtPeso != null : "fx:id=\"txtPeso\" was not injected: check your FXML file 'usuario.fxml'.";

        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.1:3306/bdMyDieta", "root", "root");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Usuario", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();
            rs.first();
            txtNombre_usuario.setText(rs.getString("nombre_usuario"));
            txtAltura.setText(Integer.toString(rs.getInt("altura")));
            txtEdad.setText(Integer.toString(rs.getInt("edad")));
            txtPeso.setText(Integer.toString(rs.getInt("peso")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
