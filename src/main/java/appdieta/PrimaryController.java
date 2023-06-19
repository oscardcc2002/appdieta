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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PrimaryController {

    ResultSet rs;
    Connection con;
    String sexo = "";

    @FXML
    private Pane titlePane;

    @FXML
    private ImageView btnMinimize;

    @FXML
    private ImageView btnClose;

    @FXML
    private TextField txtNombre;

    @FXML
    private RadioButton btnHombre;

    @FXML
    private RadioButton btnMujer;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtAltura;

    @FXML
    private Button btnEnviar;

    @FXML
    private ToggleGroup sexoToggleGroup;

    @FXML
    void onHombreClicked(ActionEvent event) {
        sexo = "Hombre";
    }

    @FXML
    void onMujerClicked(ActionEvent event) {
        sexo = "Mujer";
    }

    @FXML
    void switchToVolumenDefinicion(ActionEvent event) throws IOException {
        try {
            String nombre_usuario = txtNombre.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            int peso = Integer.parseInt(txtPeso.getText());
            int altura = Integer.parseInt(txtAltura.getText());

            String sexo = this.sexo;

            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO Usuario (nombre_usuario, edad, peso, altura, sexo) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, nombre_usuario);
            stmt.setInt(2, edad);
            stmt.setInt(3, peso);
            stmt.setInt(4, altura);
            stmt.setString(5, sexo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Error al acceder a la base de datos: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

        App.setRoot("volumenDefinicion");
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
        assert titlePane != null : "fx:id=\"titlePane\" was not injected: check your FXML file 'primary.fxml'.";
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'primary.fxml'.";
        assert btnMinimize != null : "fx:id=\"btnMinimize\" was not injected: check your FXML file 'primary.fxml'.";
        assert txtNombre != null : "fx:id=\"txtNombre\" was not injected: check your FXML file 'primary.fxml'.";
        assert btnHombre != null : "fx:id=\"btnHombre\" was not injected: check your FXML file 'primary.fxml'.";
        assert btnMujer != null : "fx:id=\"btnMujer\" was not injected: check your FXML file 'primary.fxml'.";
        assert txtEdad != null : "fx:id=\"txtEdad\" was not injected: check your FXML file 'primary.fxml'.";
        assert txtPeso != null : "fx:id=\"txtPeso\" was not injected: check your FXML file 'primary.fxml'.";
        assert txtAltura != null : "fx:id=\"txtAltura\" was not injected: check your FXML file 'primary.fxml'.";
        assert btnEnviar != null : "fx:id=\"btnEnviar\" was not injected: check your FXML file 'primary.fxml'.";
        sexoToggleGroup = new ToggleGroup();
        btnHombre.setToggleGroup(sexoToggleGroup);
        btnMujer.setToggleGroup(sexoToggleGroup);

        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.1:3306/bdMyDieta", "root", "root");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Usuario", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
