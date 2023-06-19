package appdieta;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class registros {
    Connection con;
    ResultSet rs;

    @FXML
    private Button btnborrar;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView btnClose;

    @FXML
    private Button btnComidas;

    @FXML
    private ImageView btnMinimize;

    @FXML
    private Button btnPlanDeDieta;

    @FXML
    private Button btnUsuario;

    @FXML
    private TableColumn<nuevoRegistro, Integer> cantidadAlimentoTabla;

    @FXML
    private TableColumn<nuevoRegistro, Date> fechayhoraTabla;

    @FXML
    private Button insertar;

    @FXML
    private TableColumn<nuevoRegistro, String> nombreAlimentoTabla;

    @FXML
    private TableColumn<nuevoRegistro, String> nombreComidaTabla;

    @FXML
    private TableView<nuevoRegistro> tablaRegistros;

    @FXML
    private Pane titlePane;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtFechayHora;

    @FXML
    private TextField txtNombreAlimento;

    @FXML
    private TextField txtNombreComida;

    @FXML
    ObservableList<nuevoRegistro> listaRegistros;

    private int generarNumeroAleatorio2 = generarNumeroAleatorio();

    private int generarNumeroAleatorio() {
        Random random = new Random();
        return random.nextInt(10000); // Genera un número aleatorio entre 0 y 9999
    }

    @FXML
    void insertar(ActionEvent event) {

        String nombreComida = txtNombreComida.getText();
        String nombreAlimento = txtNombreAlimento.getText();

        if (nombreComida.isEmpty() || nombreAlimento.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debes completar los campos de nombre y alimento");
            alert.showAndWait();
            return;
        }

        try {
            int generarNumeroAleatorio2 = generarNumeroAleatorio();
            PreparedStatement stmt2 = con.prepareStatement(
                    "INSERT INTO Comidas (id_comidas, fecha_Y_Hora, nombre_comida, nombre_alimento, cantidad) VALUES (?,?,?,?,?)");
            stmt2.setInt(1, generarNumeroAleatorio2);
            stmt2.setString(2, txtFechayHora.getText());
            stmt2.setString(3, nombreComida);
            stmt2.setString(4, nombreAlimento);
            stmt2.setString(5, txtCantidad.getText());
            stmt2.executeUpdate();
            String query = "SELECT * FROM Comidas";
            stmt2 = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt2.executeQuery(query);
            rs.first(); // El último, si corresponde al insertado
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaYHora = dateFormat.format(rs.getDate("fecha_Y_Hora"));
            txtFechayHora.setText(fechaYHora);
            txtNombreComida.setText(rs.getString("nombre_comida"));
            txtNombreAlimento.setText(rs.getString("nombre_alimento"));
            txtCantidad.setText(Integer.toString(rs.getInt("cantidad")));
            txtFechayHora.clear();
            txtNombreComida.clear();
            txtNombreAlimento.clear();
            txtCantidad.clear();
            actualizarTabla();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Error al acceder a la base de datos: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void borrar(ActionEvent event) {
        nuevoRegistro registroSeleccionado = tablaRegistros.getSelectionModel().getSelectedItem();
        if (registroSeleccionado == null) {
            // No hay fila seleccionada
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No hay fila seleccionada");
            alert.setContentText("Seleccione una fila para eliminarla.");
            alert.showAndWait();
            return;
        }

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Comidas WHERE id_comidas = ?");
            stmt.setInt(1, registroSeleccionado.getId_comidas());
            stmt.executeUpdate();

            // Eliminar el registro de la lista
            listaRegistros.remove(registroSeleccionado);

            // Limpiar los campos de texto
            txtFechayHora.clear();
            txtNombreComida.clear();
            txtNombreAlimento.clear();
            txtCantidad.clear();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Error al acceder a la base de datos: " + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
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
    void switchToPlanDeDieta(ActionEvent event) throws IOException {
        App.setRoot("planDeDieta");
    }

    @FXML
    void switchToUsuario(ActionEvent event) throws IOException {
        App.setRoot("usuario");
    }

    @FXML
    void switchToComidas(ActionEvent event) throws IOException {
        App.setRoot("comidas");
    }

    void actualizarTabla() throws SQLException {
        listaRegistros.clear();
        try {
            nuevoRegistro.llenarRegistros(listaRegistros);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void initialize() {
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'registros.fxml'.";
        assert btnComidas != null : "fx:id=\"btnComidas\" was not injected: check your FXML file 'registros.fxml'.";
        assert btnMinimize != null : "fx:id=\"btnMinimize\" was not injected: check your FXML file 'registros.fxml'.";
        assert btnPlanDeDieta != null
                : "fx:id=\"btnPlanDeDieta\" was not injected: check your FXML file 'registros.fxml'.";
        assert btnUsuario != null : "fx:id=\"btnUsuario\" was not injected: check your FXML file 'registros.fxml'.";
        assert btnborrar != null : "fx:id=\"btnborrar\" was not injected: check your FXML file 'registros.fxml'.";
        assert cantidadAlimentoTabla != null
                : "fx:id=\"cantidadAlimentoTabla\" was not injected: check your FXML file 'registros.fxml'.";
        assert fechayhoraTabla != null
                : "fx:id=\"fechayhoraTabla\" was not injected: check your FXML file 'registros.fxml'.";
        assert insertar != null : "fx:id=\"insertar\" was not injected: check your FXML file 'registros.fxml'.";
        assert nombreAlimentoTabla != null
                : "fx:id=\"nombreAlimentoTabla\" was not injected: check your FXML file 'registros.fxml'.";
        assert nombreComidaTabla != null
                : "fx:id=\"nombreComidaTabla\" was not injected: check your FXML file 'registros.fxml'.";
        assert tablaRegistros != null
                : "fx:id=\"tablaRegistros\" was not injected: check your FXML file 'registros.fxml'.";
        assert titlePane != null : "fx:id=\"titlePane\" was not injected: check your FXML file 'registros.fxml'.";
        assert txtCantidad != null : "fx:id=\"txtCantidad\" was not injected: check your FXML file 'registros.fxml'.";
        assert txtFechayHora != null
                : "fx:id=\"txtFechayHora\" was not injected: check your FXML file 'registros.fxml'.";
        assert txtNombreAlimento != null
                : "fx:id=\"txtNombreAlimento\" was not injected: check your FXML file 'registros.fxml'.";
        assert txtNombreComida != null
                : "fx:id=\"txtNombreComida\" was not injected: check your FXML file 'registros.fxml'.";

        tablaRegistros.setOnMouseClicked(event -> {
            nuevoRegistro registroSeleccionado = tablaRegistros.getSelectionModel().getSelectedItem();
            if (registroSeleccionado != null) {
                txtFechayHora.setText(registroSeleccionado.getFecha_Y_Hora().toString());
                txtNombreComida.setText(registroSeleccionado.getNombre_comida());
                txtNombreAlimento.setText(registroSeleccionado.getNombre_alimento());
                txtCantidad.setText(Integer.toString(registroSeleccionado.getCantidad()));
            }
        });

        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.1:3306/bdMyDieta", "root", "root");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Comidas", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Hay al menos un registro en el conjunto de resultados
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaYHora = dateFormat.format(rs.getDate("fecha_Y_Hora"));
                txtFechayHora.setText(fechaYHora);
                txtNombreComida.setText(rs.getString("nombre_comida"));
                txtNombreAlimento.setText(rs.getString("nombre_alimento"));
                txtCantidad.setText(Integer.toString(rs.getInt("cantidad")));
            } else {
                // No hay registros en el conjunto de resultados
                // Puedes manejar esta situación de acuerdo a tus requerimientos, por ejemplo,
                // mostrando un mensaje o tomando alguna otra acción.
            }

            listaRegistros = FXCollections.observableArrayList();

            tablaRegistros.setItems(listaRegistros);
            fechayhoraTabla.setCellValueFactory(new PropertyValueFactory<>("fecha_Y_Hora"));
            nombreComidaTabla.setCellValueFactory(new PropertyValueFactory<>("nombre_comida"));
            nombreAlimentoTabla.setCellValueFactory(new PropertyValueFactory<>("nombre_alimento"));
            cantidadAlimentoTabla.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            nuevoRegistro.llenarRegistros(listaRegistros);
            txtFechayHora.setText("");
            txtNombreComida.setText("");
            txtNombreAlimento.setText("");
            txtCantidad.setText("");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
