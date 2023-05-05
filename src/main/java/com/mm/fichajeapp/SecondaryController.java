package com.mm.fichajeapp;

import com.mm.fichajeapp.modelo.DataManagement;
import com.mm.fichajeapp.modelo.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

    DataManagement dm = new DataManagement();

    public void initialize() {

        DNI.setCellValueFactory(new PropertyValueFactory<Worker, String>("dni_trabajador"));
        Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_trabajador"));
        Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido_trabajador"));
        Horas_trabajadas.setCellValueFactory(new PropertyValueFactory<>("horas_fichadas_trabajador"));
        tableWorkers.setItems(dm.getTableWorkersAstList());

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
        if (horas.getText() == "" || minutos.getText() == "")
            return;
        int hours, minutes;
        hours = Integer.parseInt(horas.getText()) <= 24 && Integer.parseInt(horas.getText()) >= 0
                ? Integer.parseInt(horas.getText())
                : -1;
        minutes = Integer.parseInt(horas.getText()) <= 60 && Integer.parseInt(horas.getText()) >= 0
                ? Integer.parseInt(horas.getText())
                : -1;
        double time;
        if (hours == -1 || minutes == -1 || (hours == 0 && minutes == 0))
            return;
        else
            time = (double) hours + (double) (minutes / 60 * 100);
        if (horario.getSelectionModel().getSelectedItem() != null)
            dm.signTime(tableWorkers.getSelectionModel().getSelectedItem().getDni_trabajador(),
                    Integer.parseInt(String.valueOf(horario.getSelectionModel().getSelectedItem().charAt(0))), time);
        tableWorkers.getItems().clear();
        tableWorkers.setItems(dm.getTableWorkersAstList());
    }
}