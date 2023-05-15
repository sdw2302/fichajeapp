package com.mm.fichajeapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.time.LocalDate;

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
    TableColumn<Worker, LocalDate> FechaNacimiento;
    @FXML
    TableColumn<Worker, String> EmpresaResponsable;

    @FXML
    ImageView logoutImg;

    DataManagement dm = new DataManagement();

    public void initialize() {

        DNI.setCellValueFactory(new PropertyValueFactory<>("dni_trabajador"));
        Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_trabajador"));
        Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido_trabajador"));
        FechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        EmpresaResponsable.setCellValueFactory(new PropertyValueFactory<>("empresa_responsable"));
        tableWorkers.setItems(dm.getTableWorkersCompleteAsList());

        ObservableList<String> nombreEmpresa = dm.getNameCompanies();
        if (nombreEmpresa != null) {
            for (String string : nombreEmpresa)
                if (string != "")
                    EmpresaResponsableToAdd.getItems().add(string);
        }

    }

    public void switchToPrimary() throws IOException{
        App.setRoot("primary");
    }

    public void switchToSecomdary() throws IOException {
        App.setRoot("secondary");
    }

    public void switchToCreateWorker() throws IOException {
        App.setRoot("createWorker");
    }

    public void switchToScheduleManagement() throws IOException {
        App.setRoot("scheduleManagement");
    }

    public void createWorker() {
        if (NIFToAdd.getText().equals("") || NombreToAdd.getText().equals("") || ApellidoToAdd.getText().equals("")
                || FechaNacimientoToAdd.getValue() == null
                || EmpresaResponsableToAdd.getSelectionModel().getSelectedItem() == null) {
            this.createAlert("Faltan datos");
            return;
        }
        boolean created = dm.createWorker(NIFToAdd.getText(), NombreToAdd.getText(), ApellidoToAdd.getText(),
                FechaNacimientoToAdd.getValue(), EmpresaResponsableToAdd.getSelectionModel().getSelectedItem());
        if (!created)
            this.createAlert("Ha ocurrido un error de servidor");
        tableWorkers.getItems().clear();
        tableWorkers.setItems(dm.getTableWorkersCompleteAsList());
    }

    public void deleteWorker() {

        if (tableWorkers.getSelectionModel().getSelectedItem() == null) {
            this.createAlert("Seleccione un trabajador");
            return;
        }
        boolean deleted = dm.deleteWorker(tableWorkers.getSelectionModel().getSelectedItem());
        if (!deleted)
            this.createAlert("Ha ocurrido un error de servidor");
        tableWorkers.getItems().clear();
        tableWorkers.setItems(dm.getTableWorkersCompleteAsList());
    }

    private void createAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText(message);
        alert.showAndWait();

    }
}
