package com.mm.fichajeapp;

import java.io.IOException;

import com.mm.fichajeapp.modelo.DataManagement;
import com.mm.fichajeapp.modelo.Worker;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SecondaryController {
     
    @FXML
    TextField NIF;

    @FXML
    ComboBox horario;

    @FXML
    TableView tableWorkers;

    @FXML
    TableColumn<Worker,String> DNI;
    @FXML
    TableColumn<Worker,String> Nombre;
    @FXML
    TableColumn<Worker,String> Apellido;
    @FXML
    TableColumn<Worker,String> Horas_trabajadas;



    DataManagement dm = new DataManagement();

    public void initialize(){
        
        dm.getList();

        DNI.setCellValueFactory(new PropertyValueFactory<>("DNI"));

        tableWorkers.setItems(dm.getList());        
    }
}