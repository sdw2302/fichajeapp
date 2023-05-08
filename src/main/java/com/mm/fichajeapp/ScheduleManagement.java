package com.mm.fichajeapp;

import com.mm.fichajeapp.modelo.DataManagement;
import com.mm.fichajeapp.modelo.Schedule;

import javafx.fxml.FXML;
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

    DataManagement dm = new DataManagement();

    public void initialize() {
        time_inicio.setCellValueFactory(new PropertyValueFactory<Schedule, String>("hora_inicio"));
        time_final.setCellValueFactory(new PropertyValueFactory<Schedule, String>("hora_final"));
        time_descanso.setCellValueFactory(new PropertyValueFactory<Schedule, String>("descanso"));
        table.setItems(dm.getTableSchedulesAsList());
    }

    public void createSchedule() {
        if (hours_inicio.getText() == "" || minutes_inicio.getText() == "" || hours_final.getText() == ""
                || minutes_final.getText() == "" || hours_descanso.getText() == "" || minutes_descanso.getText() == "")
            return;
        int checkhours_inicio = Integer.parseInt(hours_inicio.getText()),
                checkminutes_inicio = Integer.parseInt(minutes_inicio.getText()),
                checkhours_final = Integer.parseInt(hours_final.getText()),
                checkminutes_final = Integer.parseInt(minutes_final.getText()),
                checkhours_descanso = Integer.parseInt(hours_descanso.getText()),
                checkminutes_descanso = Integer.parseInt(minutes_descanso.getText());

        if (checkhours_inicio < 0 || checkhours_inicio >= 24 || checkminutes_inicio < 0 || checkminutes_inicio >= 60
                || checkhours_final < 0 || checkhours_final >= 24 || checkminutes_final < 0 || checkminutes_final >= 60
                || checkhours_descanso < 0 || checkhours_descanso >= 24 || checkminutes_descanso < 0
                || checkminutes_descanso >= 60)
            return;
        String inicioTime = hours_inicio.getText();
        inicioTime += checkminutes_inicio >= 45 ? ".75"
                : checkminutes_inicio >= 30 ? ".50" : checkminutes_inicio >= 15 ? ".25" : ".00";
        String finalTime = hours_final.getText();
        finalTime += checkminutes_final >= 45 ? ".75"
                : checkminutes_final >= 30 ? ".50" : checkminutes_final >= 15 ? ".25" : ".00";
        String descansoTime = hours_descanso.getText();
        descansoTime += checkminutes_descanso >= 45 ? ".75"
                : checkminutes_descanso >= 30 ? ".50" : checkminutes_descanso >= 15 ? ".25" : ".00";
        dm.createSchedule(inicioTime, finalTime, descansoTime);
        table.getItems().clear();
        table.setItems(dm.getTableSchedulesAsList());
    }

    public void changeToFichar() {

    }

    public void changeMenuToCreateWorker() {

    }
}
