package com.mm.fichajeapp.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManagement {
    public ObservableList<Worker> getList() {
        ObservableList<Worker> trabajadores = FXCollections.observableArrayList();
        String sql = "select nombre_trabajador, apellido_trabajador, dni_trabajador, horas_fichadas_trabajador from trabajador";

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
