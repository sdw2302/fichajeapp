package com.mm.fichajeapp;

import com.mm.fichajeapp.modelo.DataManagement;
import com.mm.fichajeapp.modelo.Worker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SecondaryController {

    @FXML
    TextField NIF;

    @FXML
    ComboBox<String> horario;

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
    Button loadSchedules;

    DataManagement dm = new DataManagement();

    Worker w = new Worker(null, null, null, null);

    public void initialize() {

        // ObservableList<Worker> trabajadores = dm.geTableAstList();

        ObservableList<Worker> listaTrabajadores = w.todosTrabajadores();

        // DNI.setCellValueFactory(new PropertyValueFactory<>("dni_trabajador"));
        DNI.setCellValueFactory(new PropertyValueFactory<Worker, String>("dni_trabajador"));
        Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_trabajador"));
        Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido_trabajador"));
        Horas_trabajadas.setCellValueFactory(new PropertyValueFactory<>("horas_fichadas_trabajador"));
        tableWorkers.setItems(listaTrabajadores);

    }

    public void loadSchedulesClick() {
        horario.getItems().clear();
        String nif = NIF.getText();
        String[] schedules = dm.loadSchedules(nif).split(";");
        for (String string : schedules) {
            horario.getItems().add(string);
        }
    }
}