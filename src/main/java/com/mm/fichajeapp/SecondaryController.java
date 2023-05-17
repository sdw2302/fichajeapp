package com.mm.fichajeapp;

import java.io.IOException;

import com.mm.fichajeapp.modelo.DataManagement;
import com.mm.fichajeapp.modelo.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SecondaryController {

    @FXML
    ComboBox<String> horario;

    @FXML
    TextField horas;

    @FXML
    TextField minutos;

    @FXML
    Button botonFichar;

    // TABLE //
    @FXML
    TableView<Worker> tableWorkers;

    @FXML
    TableColumn<Worker, String> DNI;
    @FXML
    TableColumn<Worker, String> Nombre;
    @FXML
    TableColumn<Worker, String> Apellido;
    @FXML
    TableColumn<Worker, Double> Horas_trabajadas;

    // Menu //
    @FXML
    MenuButton optionMenu;

    @FXML
    MenuItem FicharMenu;
    @FXML
    MenuItem CreateWorkerMenu;

    @FXML
    Button SwitchToSecondary;
    @FXML
    Button SwitchToCreateWorker;
    @FXML
    Button SwitchToScheduleManagement;

    DataManagement dm = new DataManagement();

    public void initialize() {
        // Configure cell value factories for the table columns
        DNI.setCellValueFactory(new PropertyValueFactory<Worker, String>("dni_trabajador"));
        Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_trabajador"));
        Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido_trabajador"));
        Horas_trabajadas.setCellValueFactory(new PropertyValueFactory<>("horas_fichadas_trabajador"));
        // Set the items of the table view using the data from DataManagement
        tableWorkers.setItems(dm.getTableWorkersAsList());
    }

    public void switchToPrimary() throws IOException{
        // Switch to the primary view
        App.setRoot("primary");
    }

    public void switchToSecomdary() throws IOException {
        // Switch to the secondary view
        App.setRoot("secondary");
    }

    public void switchToCreateWorker() throws IOException {
        // Switch to the createWorker view
        App.setRoot("createWorker");
    }

    public void switchToScheduleManagement() throws IOException {
        // Switch to the scheduleManagement view
        App.setRoot("scheduleManagement");
    }


    public void signTimeWorked() {
        // Check if the required fields are empty or not selected
        if (horas.getText().isEmpty() || minutos.getText().isEmpty()
                || horario.getSelectionModel().getSelectedItem() == null) {
            // Display an error alert for missing data
            this.createAlert("Faltan datos");
            return;
        }
        
        // Parse the input hours and minutes as integers
        int hours, minutes;
        hours = Integer.parseInt(horas.getText()) <= 24 && Integer.parseInt(horas.getText()) >= 0
                ? Integer.parseInt(horas.getText())
                : -1;
        minutes = Integer.parseInt(minutos.getText()) <= 60 && Integer.parseInt(minutos.getText()) >= 0
                ? Integer.parseInt(minutos.getText())
                : -1;

        String time;
        // Check if the parsed hours and minutes are valid
        if (hours == -1 || minutes == -1 || (hours == 0 && minutes == 0)) {
            // Display an error alert for invalid values
            this.createAlert("Valores no validos");
            return;
        }
        
        // Construct the time string based on the parsed hours and minutes
        time = String.valueOf(hours);
        time += minutes >= 45 ? ".75" : minutes >= 30 ? ".50" : minutes >= 15 ? ".25" : "";
        
        // Call the "signTime" method in DataManagement to sign the worked time
        boolean signed = dm.signTime(tableWorkers.getSelectionModel().getSelectedItem().getDni_trabajador(),
                Integer.parseInt(String.valueOf(horario.getSelectionModel().getSelectedItem().charAt(0))), time);
        
        if (!signed)
            // Display an error alert if signing the time fails
            this.createAlert("Ha ocurrido un error de servidor");
        
        // Clear and refresh the table view
        tableWorkers.getItems().clear();
        tableWorkers.setItems(dm.getTableWorkersAsList());
    }

    private void createAlert(String message) {
        // Create and display an error alert with the given message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}