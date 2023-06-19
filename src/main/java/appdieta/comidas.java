package appdieta;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalTime;

public class comidas {
    LocalTime currentTime;
    String comida;
    Connection con;
    ResultSet rs;
    ResultSet planResult;
    String nombreAlimento;

    @FXML
    private Button botonAnterior;

    @FXML
    private Button botonFin;

    @FXML
    private Button botonInicio;

    @FXML
    private Button botonSiguiente;

    @FXML
    private Button btnPlanDeDieta;

    @FXML
    private Button btnRegistros;

    @FXML
    private Button btnUsuario;
    @FXML
    private ImageView imagenComida;

    @FXML
    private Label lblComidaHora;

    @FXML
    private Pane titlePane;

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnMinimize;

    @FXML
    private Label lblCalorias;

    @FXML
    private Label lblCarbohidratos;

    @FXML
    private Label lblGrasas;

    @FXML
    private Label lblProteinas;

    @FXML
    private Label lblTipo;

    @FXML
    private Label lblNombreAlimento;

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
    void switchToPlanDeDieta(ActionEvent event) throws IOException {
        App.setRoot("planDeDieta");
    }

    @FXML
    void switchToUsuario(ActionEvent event) throws IOException {
        App.setRoot("usuario");
    }

    @FXML
    void switchToRegistros(ActionEvent event) throws IOException {
        App.setRoot("registros");
    }

    private String obtenerComida(LocalTime time) {
        if (time.isBefore(LocalTime.of(6, 0)) || time.equals(LocalTime.of(6, 0))) {
            return "Cena";
        } else if (time.isBefore(LocalTime.of(13, 0))) {
            return "Desayuno";
        } else if (time.isBefore(LocalTime.of(17, 0))) {
            return "Comida";
        } else if (time.isBefore(LocalTime.of(20, 0))) {
            return "Merienda";
        } else {
            return "Cena";
        }
    }

    @FXML
    void botonAnterior(ActionEvent event) throws SQLException, FileNotFoundException {

        rs.previous();
        lblTipo.setText(rs.getString("categoría"));
        lblNombreAlimento.setText(rs.getString("nombreAlimento"));
        nombreAlimento = rs.getString("nombreAlimento");
        lblCarbohidratos.setText(Double.toString(rs.getDouble("carbohidratosAlimentos")));
        lblProteinas.setText(Double.toString(rs.getDouble("proteínasAlimento")));
        lblGrasas.setText(Double.toString(rs.getDouble("grasasAlimento")));
        lblCalorias.setText(Double.toString(rs.getDouble("caloríasAlimento")));
        Image image = new Image(
                new FileInputStream("C:/Users/You/Desktop/MyDieta/appdieta/src/main/resources/appdieta/images/"
                        + nombreAlimento + ".png"));

        imagenComida.setImage(image);

        if (rs.isFirst()) {
            botonInicio.setDisable(true);
            botonAnterior.setDisable(true);
        } else {
            botonInicio.setDisable(false);
            botonAnterior.setDisable(false);
        }
        if (!rs.isLast()) {
            botonSiguiente.setDisable(false);
            botonFin.setDisable(false);
        }

    }

    @FXML
    void botonFin(ActionEvent event) throws SQLException, FileNotFoundException {

        rs.last();
        lblTipo.setText(rs.getString("categoría"));
        lblNombreAlimento.setText(rs.getString("nombreAlimento"));
        nombreAlimento = rs.getString("nombreAlimento");
        lblCarbohidratos.setText(Double.toString(rs.getDouble("carbohidratosAlimentos")));
        lblProteinas.setText(Double.toString(rs.getDouble("proteínasAlimento")));
        lblGrasas.setText(Double.toString(rs.getDouble("grasasAlimento")));
        lblCalorias.setText(Double.toString(rs.getDouble("caloríasAlimento")));
        Image image = new Image(
                new FileInputStream("C:/Users/You/Desktop/MyDieta/appdieta/src/main/resources/appdieta/images/"
                        + nombreAlimento + ".png"));

        imagenComida.setImage(image);

        if (rs.isLast()) {
            botonSiguiente.setDisable(true);
            botonFin.setDisable(true);
        } else {
            botonSiguiente.setDisable(false);
            botonFin.setDisable(false);
        }
        if (!rs.isFirst()) {
            botonAnterior.setDisable(false);
            botonInicio.setDisable(false);
        }

    }

    @FXML
    void botonInicio(ActionEvent event) throws SQLException, FileNotFoundException {

        rs.first();
        lblTipo.setText(rs.getString("categoría"));
        lblNombreAlimento.setText(rs.getString("nombreAlimento"));
        nombreAlimento = rs.getString("nombreAlimento");
        lblCarbohidratos.setText(Double.toString(rs.getDouble("carbohidratosAlimentos")));
        lblProteinas.setText(Double.toString(rs.getDouble("proteínasAlimento")));
        lblGrasas.setText(Double.toString(rs.getDouble("grasasAlimento")));
        lblCalorias.setText(Double.toString(rs.getDouble("caloríasAlimento")));
        Image image = new Image(
                new FileInputStream("C:/Users/You/Desktop/MyDieta/appdieta/src/main/resources/appdieta/images/"
                        + nombreAlimento + ".png"));

        imagenComida.setImage(image);
        if (rs.isFirst()) {
            botonInicio.setDisable(true);
            botonAnterior.setDisable(true);
        } else {
            botonInicio.setDisable(false);
            botonAnterior.setDisable(false);
        }
        if (!rs.isLast()) {
            botonSiguiente.setDisable(false);
            botonFin.setDisable(false);
        }

    }

    @FXML
    void botonSiguiente(ActionEvent event) throws SQLException, FileNotFoundException {

        rs.next();
        lblTipo.setText(rs.getString("categoría"));
        lblNombreAlimento.setText(rs.getString("nombreAlimento"));
        nombreAlimento = rs.getString("nombreAlimento");
        lblCarbohidratos.setText(Double.toString(rs.getDouble("carbohidratosAlimentos")));
        lblProteinas.setText(Double.toString(rs.getDouble("proteínasAlimento")));
        lblGrasas.setText(Double.toString(rs.getDouble("grasasAlimento")));
        lblCalorias.setText(Double.toString(rs.getDouble("caloríasAlimento")));
        Image image = new Image(
                new FileInputStream("C:/Users/You/Desktop/MyDieta/appdieta/src/main/resources/appdieta/images/"
                        + nombreAlimento + ".png"));

        imagenComida.setImage(image);

        if (rs.isLast()) {
            botonSiguiente.setDisable(true);
            botonFin.setDisable(true);
        } else {
            botonSiguiente.setDisable(false);
            botonFin.setDisable(false);

        }
        if (!rs.isFirst()) {
            botonAnterior.setDisable(false);
            botonInicio.setDisable(false);
        }
    }

    @FXML
    void initialize() throws FileNotFoundException {
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'comidas.fxml'.";
        assert btnMinimize != null : "fx:id=\"btnMinimize\" was not injected: check your FXML file 'comidas.fxml'.";
        assert btnPlanDeDieta != null
                : "fx:id=\"btnPlanDeDieta\" was not injected: check your FXML file 'comidas.fxml'.";
        assert btnRegistros != null : "fx:id=\"btnRegistros\" was not injected: check your FXML file 'comidas.fxml'.";
        assert btnUsuario != null : "fx:id=\"btnUsuario\" was not injected: check your FXML file 'comidas.fxml'.";
        assert imagenComida != null : "fx:id=\"imagenComida\" was not injected: check your FXML file 'comidas.fxml'.";
        assert lblCalorias != null : "fx:id=\"lblCalorias\" was not injected: check your FXML file 'comidas.fxml'.";
        assert lblCarbohidratos != null
                : "fx:id=\"lblCarbohidratos\" was not injected: check your FXML file 'comidas.fxml'.";
        assert lblComidaHora != null : "fx:id=\"lblComidaHora\" was not injected: check your FXML file 'comidas.fxml'.";
        assert lblGrasas != null : "fx:id=\"lblGrasas\" was not injected: check your FXML file 'comidas.fxml'.";
        assert lblNombreAlimento != null
                : "fx:id=\"lblNombreAlimento\" was not injected: check your FXML file 'comidas.fxml'.";
        assert lblProteinas != null : "fx:id=\"lblProteinas\" was not injected: check your FXML file 'comidas.fxml'.";
        assert lblTipo != null : "fx:id=\"lblTipo\" was not injected: check your FXML file 'comidas.fxml'.";
        assert titlePane != null : "fx:id=\"titlePane\" was not injected: check your FXML file 'comidas.fxml'.";

        currentTime = LocalTime.now();
        comida = obtenerComida(currentTime);
        lblComidaHora.setText(comida);

        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.1:3306/bdMyDieta", "root", "root");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Alimentos", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();
            rs.first();
            /*
             * PreparedStatement planStmt =
             * con.prepareStatement("SELECT * FROM PlanDeDieta ",
             * ResultSet.TYPE_SCROLL_INSENSITIVE,
             * ResultSet.CONCUR_UPDATABLE);
             * planResult = planStmt.executeQuery();
             * planResult.first();
             */
            lblTipo.setText(rs.getString("categoría"));
            lblNombreAlimento.setText(rs.getString("nombreAlimento"));
            nombreAlimento = rs.getString("nombreAlimento");
            lblCarbohidratos.setText(Double.toString(rs.getDouble("carbohidratosAlimentos")));
            lblProteinas.setText(Double.toString(rs.getDouble("proteínasAlimento")));
            lblGrasas.setText(Double.toString(rs.getDouble("grasasAlimento")));
            lblCalorias.setText(Double.toString(rs.getDouble("caloríasAlimento")));

            Image image = new Image(
                    new FileInputStream("C:/Users/You/Desktop/MyDieta/appdieta/src/main/resources/appdieta/images/"
                            + nombreAlimento + ".png"));

            imagenComida.setImage(image);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
