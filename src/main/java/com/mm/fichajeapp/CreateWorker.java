package com.mm.fichajeapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
        // Set up cell value factories for table columns
        DNI.setCellValueFactory(new PropertyValueFactory<>("dni_trabajador"));
        Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_trabajador"));
        Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido_trabajador"));
        FechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        EmpresaResponsable.setCellValueFactory(new PropertyValueFactory<>("empresa_responsable"));

        // Set the table items to the list of complete workers from DataManagement
        tableWorkers.setItems(dm.getTableWorkersCompleteAsList());

        // Get the list of company names from DataManagement
        ObservableList<String> nombreEmpresa = dm.getNameCompanies();
        if (nombreEmpresa != null) {
            // Add non-empty company names to the ComboBox
            for (String string : nombreEmpresa) {
                if (string != "") {
                    EmpresaResponsableToAdd.getItems().add(string);
                }
            }
        }
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

    public void createWorker() {
        // Check if any required fields are empty
        if (NIFToAdd.getText().equals("") || NombreToAdd.getText().equals("") || ApellidoToAdd.getText().equals("")
                || FechaNacimientoToAdd.getValue() == null
                || EmpresaResponsableToAdd.getSelectionModel().getSelectedItem() == null) {
            this.createAlert("Faltan datos");
            return;
        }
        // Create a worker using the provided information
        boolean created = dm.createWorker(NIFToAdd.getText(), NombreToAdd.getText(), ApellidoToAdd.getText(),
                FechaNacimientoToAdd.getValue(), EmpresaResponsableToAdd.getSelectionModel().getSelectedItem());
        if (!created)
            this.createAlert("Ha ocurrido un error de servidor");
        // Clear and update the table view with the updated list of workers
        tableWorkers.getItems().clear();
        tableWorkers.setItems(dm.getTableWorkersCompleteAsList());
    }
    
    public void deleteWorker() {
        // Check if a worker is selected
        if (!this.checkIfSelected("Seleccione un trabajador"))
            return;
        // Delete the selected worker
        boolean deleted = dm.deleteWorker(tableWorkers.getSelectionModel().getSelectedItem());
        if (!deleted)
            this.createAlert("Ha ocurrido un error de servidor");
        // Clear and update the table view with the updated list of workers
        tableWorkers.getItems().clear();
        tableWorkers.setItems(dm.getTableWorkersCompleteAsList());
    }
    
    private void createAlert(String message) {
        // Create and display an error alert with the specified message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private boolean checkIfSelected(String msg) {
        // Check if a worker is selected in the table view
        if (tableWorkers.getSelectionModel().getSelectedItem() == null) {
            this.createAlert(msg);
            return false;
        }
        return true;
    }
    

    public void modifyWorker() {
        // Check if a worker is selected
        if (!this.checkIfSelected("Seleccione un trabajador"))
            return;
        // Check if at least one field is provided for modification
        if (NIFToAdd.getText().isEmpty() && NombreToAdd.getText().isEmpty() && ApellidoToAdd.getText().isEmpty()
                && FechaNacimientoToAdd.getValue() == null
                && EmpresaResponsableToAdd.getSelectionModel().getSelectedItem() == null) {
            this.createAlert("Rellene o seleccione al menos un dato para modificar el trabajador");
            return;
        }
        // Modify the worker using the provided information
        if (!dm.modifyWorker(
                NombreToAdd.getText().isEmpty()
                        ? tableWorkers.getSelectionModel().getSelectedItem().getNombre_trabajador()
                        : NombreToAdd.getText(),
                ApellidoToAdd.getText().isEmpty()
                        ? tableWorkers.getSelectionModel().getSelectedItem().getApellido_trabajador()
                        : ApellidoToAdd.getText(),
                NIFToAdd.getText().isEmpty() ? tableWorkers.getSelectionModel().getSelectedItem().getDni_trabajador()
                        : NIFToAdd.getText(),
                FechaNacimientoToAdd.getValue() == null
                        ? tableWorkers.getSelectionModel().getSelectedItem().getFecha_nacimiento()
                        : FechaNacimientoToAdd.getValue(),
                EmpresaResponsableToAdd.getSelectionModel().getSelectedItem() == null
                        ? tableWorkers.getSelectionModel().getSelectedItem().getEmpresa_responsable()
                        : EmpresaResponsableToAdd.getSelectionModel().getSelectedItem(),
                tableWorkers.getSelectionModel().getSelectedItem().getDni_trabajador()))
            this.createAlert("Ha ocurrido un error de servidor");
    
        // Clear and update the table view with the updated list of workers
        tableWorkers.getItems().clear();
        tableWorkers.setItems(dm.getTableWorkersCompleteAsList());
    }
    
}
