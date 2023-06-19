package appdieta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class bienvenida {
    Connection con;
    ResultSet rs;
    ResultSet rs2;
    @FXML
    private Button btnSiguiente;

    @FXML
    private Pane titlePane;

    @FXML
    private ImageView btnMinimize;

    @FXML
    private ImageView btnClose;

    @FXML
    void switchToPrimary(ActionEvent event) throws IOException, SQLException {
        if (!rs.next()) {
            App.setRoot("primary");
            return;
        }

        if (!rs2.first()) { // Verificar si la tabla PlanDeDieta está vacía
            App.setRoot("volumenDefinicion");
            return;
        }

        String factorActividad = rs.getString("factorActividad");
        if (factorActividad == null || factorActividad.isEmpty()) {
            App.setRoot("factorActividad");
        } else {
            App.setRoot("planDeDieta");
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
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'bienvenida.fxml'.";
        assert btnMinimize != null : "fx:id=\"btnMinimize\" was not injected: check your FXML file 'bienvenida.fxml'.";
        assert btnSiguiente != null
                : "fx:id=\"btnSiguiente\" was not injected: check your FXML file 'bienvenida.fxml'.";
        assert titlePane != null : "fx:id=\"titlePane\" was not injected: check your FXML file 'bienvenida.fxml'.";

        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.1:3306/bdMyDieta", "root", "root");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Usuario", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();

            PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM PlanDeDieta",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs2 = stmt2.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
