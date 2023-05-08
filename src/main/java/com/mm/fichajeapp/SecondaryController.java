package com.mm.fichajeapp;

import java.io.IOException;

import com.mm.fichajeapp.modelo.DataManagement;
import com.mm.fichajeapp.modelo.Worker;
import javafx.fxml.FXML;
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

    DataManagement dm = new DataManagement();

    public void initialize() {

        DNI.setCellValueFactory(new PropertyValueFactory<Worker, String>("dni_trabajador"));
        Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_trabajador"));
        Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido_trabajador"));
        Horas_trabajadas.setCellValueFactory(new PropertyValueFactory<>("horas_fichadas_trabajador"));
        tableWorkers.setItems(dm.getTableWorkersAsList());
    }

    public void changeToFichar() throws IOException {
        App.setRoot("secondary");
    }

    public void changeMenuToCreateWorker() throws IOException {
        App.setRoot("createWorker");
    }

    public void changeMenuToScheduleManagement() throws IOException {
        App.setRoot("scheduleManagement");
    }

    public void loadSchedulesClick() {
        horario.getItems().clear();
        String nif = tableWorkers.getSelectionModel().getSelectedItem() != null
                ? tableWorkers.getSelectionModel().getSelectedItem().getDni_trabajador()
                : "";
        if (nif != "") {
            String[] schedules = dm.loadSchedules(nif).split(";");
            for (String string : schedules)
                if (string != "")
                    horario.getItems().add(string);
        }
    }

    public void signTimeWorked() {
        if (horas.getText() == "" || minutos.getText() == "" || horario.getSelectionModel().getSelectedItem() == null)
            return;
        int hours, minutes;
        hours = Integer.parseInt(horas.getText()) <= 24 && Integer.parseInt(horas.getText()) >= 0
                ? Integer.parseInt(horas.getText())
                : -1;
        minutes = Integer.parseInt(minutos.getText()) <= 60 && Integer.parseInt(minutos.getText()) >= 0
                ? Integer.parseInt(minutos.getText())
                : -1;

        String time;
        if (hours == -1 || minutes == -1 || (hours == 0 && minutes == 0))
            return;
        time = String.valueOf(hours);
        time += minutes >= 45 ? ".75" : minutes >= 30 ? ".50" : minutes >= 15 ? ".25" : "";

        dm.signTime(tableWorkers.getSelectionModel().getSelectedItem().getDni_trabajador(),
                Integer.parseInt(String.valueOf(horario.getSelectionModel().getSelectedItem().charAt(0))), time);
        tableWorkers.getItems().clear();
        tableWorkers.setItems(dm.getTableWorkersAsList());
    }
}