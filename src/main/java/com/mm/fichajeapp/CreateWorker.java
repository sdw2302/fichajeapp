package com.mm.fichajeapp;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import com.mm.fichajeapp.modelo.DataManagement;
import com.mm.fichajeapp.modelo.Worker;

public class CreateWorker {

    @FXML
    TextField NIFToAdd;

    @FXML
    TextField NombreToAdd;

    @FXML
    TextField ApellidoToAdd;

    @FXML
    DatePicker FechaNacimientoToAdd;

    @FXML
    ComboBox<String> EmpresaResponsableToAdd;

    @FXML
    TableView<Worker> tableWorkers;

    @FXML
    TableColumn<Worker, String> DNI;
    @FXML
    TableColumn<Worker, String> Nombre;
    @FXML
    TableColumn<Worker, String> Apellido;
    @FXML
    TableColumn<Worker, String> FechaNacimiento;
    @FXML
    TableColumn<Worker, String> EmpresaResponsable;

    @FXML
    MenuButton optionMenu;

    @FXML
    MenuItem FicharMenu;
    @FXML
    MenuItem CreateWorkerMenu;

    DataManagement dm = new DataManagement();

    public void initialize() {

        DNI.setCellValueFactory(new PropertyValueFactory<>("dni_trabajador"));
        Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_trabajador"));
        Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido_trabajador"));
        FechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        EmpresaResponsable.setCellValueFactory(new PropertyValueFactory<>("empresa_responsable"));
        tableWorkers.setItems(dm.getTableWorkersCompleteAsList());

    }

    public void switchToTimeSigning() throws IOException {
        App.setRoot("secondary");
    }

    public void switchToCreateWorker() throws IOException {
        App.setRoot("createWorker");
    }

    public void switchToScheduleManagement() throws IOException {
        App.setRoot("scheduleManagement");
    }

}
