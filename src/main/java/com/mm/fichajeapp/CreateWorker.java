package com.mm.fichajeapp;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import com.mm.fichajeapp.modelo.DataManagement;
import com.mm.fichajeapp.modelo.DbConnection;
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
    ComboBox EmpresaResponsableToAdd;

    @FXML
    TableView<Worker> tableWorkers;

    @FXML
    TableColumn<Worker,String> DNI;
    @FXML
    TableColumn<Worker,String> Nombre;
    @FXML
    TableColumn<Worker,String> Apellido;
    @FXML
    TableColumn<Worker,LocalDate> FechaNacimiento;
    @FXML
    TableColumn<Worker,String> EmpresaResponsable;

    DataManagement dm = new DataManagement();

    public void initialize(){

        DNI.setCellValueFactory(new PropertyValueFactory<>("dni_trabajador"));
        Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre_trabajador"));
        Apellido.setCellValueFactory(new PropertyValueFactory<>("apellido_trabajador"));
        FechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        tableWorkers.setItems(dm.getTableWorkersAstList());      

    }

    


}
