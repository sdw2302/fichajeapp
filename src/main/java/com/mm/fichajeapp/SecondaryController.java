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

        DNI.setCellValueFactory(new PropertyValueFactory<Worker, String>("dni_trabajador"));
        Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_trabajador"));
        Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido_trabajador"));
        Horas_trabajadas.setCellValueFactory(new PropertyValueFactory<>("horas_fichadas_trabajador"));
        tableWorkers.setItems(dm.getTableWorkersAsList());
    }

    public void switchToPrimary() throws IOException {
        // Switches to the primary view.
        App.setRoot("primary");
    }

    public void switchToSecomdary() throws IOException {
        // Switches to the secondary view.
        App.setRoot("secondary");
    }

    public void switchToCreateWorker() throws IOException {
        // Switches to the CreateWorker view.
        App.setRoot("createWorker");
    }

    public void switchToScheduleManagement() throws IOException {
        // Switches to the ScheduleManagement view.
        App.setRoot("scheduleManagement");
    }

    public void loadSchedulesClick() {
        // Clear the items in the horario ComboBox
        horario.getItems().clear();
    
        // Get the selected worker's NIF
        String nif = tableWorkers.getSelectionModel().getSelectedItem() != null
                ? tableWorkers.getSelectionModel().getSelectedItem().getDni_trabajador()
                : "";
    
        // If a worker is selected, load their schedules
        if (!nif.isEmpty()) {
            // Retrieve the schedules as a string from the data manager
            String schedulesString = dm.loadSchedules(nif);
    
            // Split the schedules string by ';' to get individual schedules
            String[] schedules = schedulesString.split(";");
    
            // Add non-empty schedules to the horario ComboBox
            for (String schedule : schedules) {
                if (!schedule.isEmpty()) {
                    horario.getItems().add(schedule);
                }
            }
        }
    }
    

    public void signTimeWorked() {
        // Check if any required fields are empty
        if (horas.getText().isEmpty() || minutos.getText().isEmpty()
                || horario.getSelectionModel().getSelectedItem() == null) {
            this.createAlert("Faltan datos");
            return;
        }
    
        // Parse hours and minutes from text fields
        int hours = Integer.parseInt(horas.getText());
        int minutes = Integer.parseInt(minutos.getText());
    
        // Check if the entered values are valid
        if (hours < 0 || hours > 24 || minutes < 0 || minutes > 60 || (hours == 0 && minutes == 0)) {
            this.createAlert("Valores no válidos");
            return;
        }
    
        // Convert hours and minutes to a time string format
        String time = String.valueOf(hours);
        time += minutes >= 45 ? ".75" : minutes >= 30 ? ".50" : minutes >= 15 ? ".25" : "";
    
        // Sign the time worked for the selected worker and schedule
        boolean signed = dm.signTime(tableWorkers.getSelectionModel().getSelectedItem().getDni_trabajador(),
                Integer.parseInt(String.valueOf(horario.getSelectionModel().getSelectedItem().charAt(0))), time);
    
        // Show an alert if signing failed
        if (!signed) {
            this.createAlert("Ha ocurrido un error de servidor");
        }
    
        // Clear and update the table of workers
        tableWorkers.getItems().clear();
        tableWorkers.setItems(dm.getTableWorkersAsList());
    }
    

    private void createAlert(String message) {
        // Create an error alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText(message);
    
        // Show the alert and wait for user confirmation
        alert.showAndWait();
    }
    
}