package com.mm.fichajeapp.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mm.fichajeapp.modelo.Worker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManagement {

    public ObservableList<Worker> geTableAstList() {

        ObservableList<Worker> trabajadores = FXCollections.observableArrayList();
        String sql = "select dni_trabajador, nombre_trabajador, apellido_trabajador,  horas_fichadas_trabajador from trabajador";

        DbConnection conn = new DbConnection();
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next())
                trabajadores.add(new Worker(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getDouble(4)));

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return trabajadores;
    }

}
