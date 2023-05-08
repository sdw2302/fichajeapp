package com.mm.fichajeapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

import com.mm.fichajeapp.modelo.DataManagement;
import com.mm.fichajeapp.modelo.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
        tableWorkers.setItems(dm.getTableWorkersAstList());

        

        // CreateWorkerMenu.setOnAction(new EventHandler<ActionEvent>() {
        //     @Override
        //     public void handle(ActionEvent event) {
        //         try { 
        //             App.setRoot("CreateWorker");
        //         } catch (IOException e) {
        //             System.out.println(e.getMessage());
        //         }
        //     }
        // });

        
        

    }

    public void changeMenu() throws IOException{
            App.setRoot("CreateWorker");
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
        // minutes = Integer.parseInt(minutos.getText()) <= 60 && Integer.parseInt(minutos.getText()) >= 0 
        //         ? Integer.parseInt(minutos.getText()) 
        //         : -1;

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