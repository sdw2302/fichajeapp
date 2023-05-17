package com.mm.fichajeapp;

import java.io.IOException;

import com.mm.fichajeapp.modelo.DataManagement;
import com.mm.fichajeapp.modelo.Schedule;
import com.mm.fichajeapp.modelo.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScheduleManagement {

    @FXML
    MenuButton optionMenu;

    @FXML
    TextField hours_inicio;

    @FXML
    TextField minutes_inicio;

    @FXML
    TextField hours_final;

    @FXML
    TextField minutes_final;

    @FXML
    TextField hours_descanso;

    @FXML
    TextField minutes_descanso;

    @FXML
    TableView<Schedule> table;

    @FXML
    TableColumn<Schedule, String> time_inicio;

    @FXML
    TableColumn<Schedule, String> time_final;

    @FXML
    TableColumn<Schedule, String> time_descanso;

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

    @FXML
    ComboBox<String> dayOfTheWeek;

    DataManagement dm = new DataManagement();

    public void initialize() {
        // Set the cell value factory for the time_inicio,final,descanso column to retrieve values from the  propertys of the "Schedule" class
        time_inicio.setCellValueFactory(new PropertyValueFactory<Schedule, String>("hora_inicio"));
        time_final.setCellValueFactory(new PropertyValueFactory<Schedule, String>("hora_final"));
        time_descanso.setCellValueFactory(new PropertyValueFactory<Schedule, String>("descanso"));

        // Set the items of the "table" object to the list of schedules obtained from the "dm" object
        table.setItems(dm.getTableSchedulesAsList());

        // Set the cell value factory for the DNI,Nombre,Apellido, Horas_trabajadas column to retrieve values from the propertys of the "Worker" class
        DNI.setCellValueFactory(new PropertyValueFactory<Worker, String>("dni_trabajador"));
        Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_trabajador"));
        Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido_trabajador"));
        Horas_trabajadas.setCellValueFactory(new PropertyValueFactory<>("horas_fichadas_trabajador"));

        // Set the cell value factory for the "DNI" column to retrieve values from the "dni_trabajador" property of the "Worker" class
        tableWorkers.setItems(dm.getTableWorkersAsList());

        // Create an array of days
        String[] days = { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" };

        // Add the days to the "dayOfTheWeek" choice box
        dayOfTheWeek.getItems().addAll(days);
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

    public void createSchedule() {
        // Check if any of the input fields are empty
        if (hours_inicio.getText() == "" || minutes_inicio.getText() == "" || hours_final.getText() == ""
                || minutes_final.getText() == "" || hours_descanso.getText() == ""
                || minutes_descanso.getText() == "") {
            this.createAlert("Faltan datos");
            return;
        }
    
        // Parse the input values to integers
        int checkhours_inicio = Integer.parseInt(hours_inicio.getText()),
                checkminutes_inicio = Integer.parseInt(minutes_inicio.getText()),
                checkhours_final = Integer.parseInt(hours_final.getText()),
                checkminutes_final = Integer.parseInt(minutes_final.getText()),
                checkhours_descanso = Integer.parseInt(hours_descanso.getText()),
                checkminutes_descanso = Integer.parseInt(minutes_descanso.getText());
    
        // Check if the input values are valid
        if (checkhours_inicio < 0 || checkhours_inicio >= 24 || checkminutes_inicio < 0 || checkminutes_inicio >= 60
                || checkhours_final < 0 || checkhours_final >= 24 || checkminutes_final < 0 || checkminutes_final >= 60
                || checkhours_descanso < 0 || checkhours_descanso >= 24 || checkminutes_descanso < 0
                || checkminutes_descanso >= 60) {
            this.createAlert("Valores no válidos");
            return;
        }
    
        // Construct the time strings with fractional values
        String inicioTime = hours_inicio.getText();
        inicioTime += checkminutes_inicio >= 45 ? ".75"
                : checkminutes_inicio >= 30 ? ".50" : checkminutes_inicio >= 15 ? ".25" : ".00";
        String finalTime = hours_final.getText();
        finalTime += checkminutes_final >= 45 ? ".75"
                : checkminutes_final >= 30 ? ".50" : checkminutes_final >= 15 ? ".25" : ".00";
        String descansoTime = hours_descanso.getText();
        descansoTime += checkminutes_descanso >= 45 ? ".75"
                : checkminutes_descanso >= 30 ? ".50" : checkminutes_descanso >= 15 ? ".25" : ".00";
    
        // Create the schedule using the time values
        boolean created = dm.createSchedule(inicioTime, finalTime, descansoTime);
    
        // Display an alert if the creation was not successful
        if (!created)
            this.createAlert("Ha ocurrido un error de servidor");
    
        // Clear and update the table view with the updated schedule list
        table.getItems().clear();
        table.setItems(dm.getTableSchedulesAsList());
    }

    public void assignSchedule() {
        // Check if all required data is selected
        if (table.getSelectionModel().getSelectedItem() == null
                || tableWorkers.getSelectionModel().getSelectedItem() == null
                || dayOfTheWeek.getSelectionModel().getSelectedItem() == null) {
            this.createAlert("Faltan datos");
            return;
        }
    
        // Get the selected day of the week
        String daySelected = dayOfTheWeek.getSelectionModel().getSelectedItem();
        int day = 0;
    
        // Convert the day of the week to a numerical value
        switch (daySelected) {
            case "Lunes":
                day = 1;
                break;
            case "Martes":
                day = 2;
                break;
            case "Miércoles":
                day = 3;
                break;
            case "Jueves":
                day = 4;
                break;
            case "Viernes":
                day = 5;
                break;
            case "Sábado":
                day = 6;
                break;
            case "Domingo":
                day = 7;
                break;
            default:
                return;
        }
    
        // Assign the selected schedule to the selected worker for the chosen day
        boolean assigned = dm.assignSchedule(
                tableWorkers.getSelectionModel().getSelectedItem().getDni_trabajador(),
                table.getSelectionModel().getSelectedItem().getId(), day);
    
        // Display an alert if the assignment was not successful
        if (!assigned)
            this.createAlert("Ha ocurrido un error de servidor");
    }
    

    private void createAlert(String message) {
        // Create and configure an error alert dialog
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText(message);
    
        // Display the alert dialog and wait for user input
        alert.showAndWait();
    }
    
}
