package appdieta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class planDeDieta {
    Connection con;

    @FXML
    private ImageView btnClose;

    @FXML
    private Button btnComidas;

    @FXML
    private ImageView btnMinimize;

    @FXML
    private Button btnRegistros;

    @FXML
    private Button btnUsuario;

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
    private Pane titlePane;

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
    void switchToUsuario(ActionEvent event) throws IOException {
        App.setRoot("usuario");
    }

    @FXML
    void initialize() {
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'planDeDieta.fxml'.";
        assert btnComidas != null : "fx:id=\"btnComidas\" was not injected: check your FXML file 'planDeDieta.fxml'.";
        assert btnMinimize != null : "fx:id=\"btnMinimize\" was not injected: check your FXML file 'planDeDieta.fxml'.";
        assert btnRegistros != null
                : "fx:id=\"btnRegistros\" was not injected: check your FXML file 'planDeDieta.fxml'.";
        assert btnUsuario != null : "fx:id=\"btnUsuario\" was not injected: check your FXML file 'planDeDieta.fxml'.";
        assert lblCalorias != null : "fx:id=\"lblCalorias\" was not injected: check your FXML file 'planDeDieta.fxml'.";
        assert lblCarbohidratos != null
                : "fx:id=\"lblCarbohidratos\" was not injected: check your FXML file 'planDeDieta.fxml'.";
        assert lblGrasas != null : "fx:id=\"lblGrasas\" was not injected: check your FXML file 'planDeDieta.fxml'.";
        assert lblProteinas != null
                : "fx:id=\"lblProteinas\" was not injected: check your FXML file 'planDeDieta.fxml'.";
        assert lblTipo != null : "fx:id=\"lblTipo\" was not injected: check your FXML file 'planDeDieta.fxml'.";
        assert titlePane != null : "fx:id=\"titlePane\" was not injected: check your FXML file 'planDeDieta.fxml'.";

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdMyDieta", "root", "root");

            // Obtener los datos del usuario
            PreparedStatement userStmt = con.prepareStatement("SELECT * FROM Usuario");
            ResultSet userResult = userStmt.executeQuery();
            userResult.next();
            int pesoInt = userResult.getInt("peso");
            int alturaInt = userResult.getInt("altura");
            double peso = (double) pesoInt;
            double altura = (double) alturaInt;
            int edad = userResult.getInt("edad");
            String sexo = userResult.getString("sexo");
            String actividad = userResult.getString("factorActividad");

            // Obtener los datos del plan de dieta
            PreparedStatement planStmt = con.prepareStatement("SELECT * FROM PlanDeDieta ");
            ResultSet planResult = planStmt.executeQuery();
            planResult.next();
            // Guardar los valores obtenidos en variables locales
            String tipo = planResult.getString("tipo");
            int id_planDeDieta = planResult.getInt("id_planDeDieta");

            // Calcular los macronutrientes totales basados en las fórmulas correspondientes
            double caloriasMantenimiento = calcularCaloriasMantenimiento(peso, altura, edad, sexo);
            double factorActividad = 1; // Factor de actividad "muy activo"
            if (actividad.equals("Ejercicio a Diario")) {
                factorActividad = 1.72;
            } else if (actividad.equals("Nada Activo")) {
                factorActividad = 1.2;
            } else if (actividad.equals("Relativamente Activo")) {
                factorActividad = 1.37;
            } else if (actividad.equals("Activo")) {
                factorActividad = 1.55;
            }

            double calorias;
            if (tipo.equals("Volumen")) {
                calorias = caloriasMantenimiento * factorActividad + 500;
            } else {
                calorias = caloriasMantenimiento * factorActividad;
            }

            double carbohidratos = calcularCarbohidratos(calorias);
            double grasas = calcularGrasas(calorias);
            double proteinas = calcularProteinas(peso);

            lblTipo.setText(tipo);

            DecimalFormat df = new DecimalFormat("#.00"); // Formato para redondear a dos decimales

            lblCarbohidratos.setText(df.format(carbohidratos));
            lblProteinas.setText(df.format(proteinas));
            lblGrasas.setText(df.format(grasas));
            lblCalorias.setText(df.format(calorias));

            PreparedStatement updateStmt = con.prepareStatement(
                    "UPDATE PlanDeDieta SET carbohidratosTotales = ?, proteinasTotales = ?, grasasTotales = ?, caloriasTotales = ? WHERE id_planDeDieta = ?");
            updateStmt.setDouble(1, carbohidratos);
            updateStmt.setDouble(2, proteinas);
            updateStmt.setDouble(3, grasas);
            updateStmt.setDouble(4, calorias);
            updateStmt.setInt(5, id_planDeDieta);
            updateStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double calcularCaloriasMantenimiento(double peso, double altura, int edad, String sexo) {
        double tasaMetabolicaBasal;

        if (sexo.equalsIgnoreCase("Hombre")) {
            tasaMetabolicaBasal = 10 * peso + 6.25 * altura - 5 * edad + 5;
        } else {
            tasaMetabolicaBasal = 10 * peso + 6.25 * altura - 5 * edad - 161;
        }

        return tasaMetabolicaBasal;
    }

    private double calcularCarbohidratos(double calorias) {
        // Supongamos que los carbohidratos aportan 4 calorías por gramo
        return (calorias * 0.50) / 4;
    }

    private double calcularGrasas(double calorias) {
        // Supongamos que 1 gramo de grasa equivale a 9 calorías
        return (calorias * 0.35) / 9;
    }

    private double calcularProteinas(double peso) {
        // Supongamos que se recomienda un consumo de 2 g de proteína por kg de peso
        // corporal
        return 2 * peso;
    }
}
