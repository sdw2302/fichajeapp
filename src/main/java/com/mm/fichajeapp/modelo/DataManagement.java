package com.mm.fichajeapp.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManagement {

    public ObservableList<Worker> getTableWorkersAstList() {

        ObservableList<Worker> trabajadores = FXCollections.observableArrayList();
        String sql = "select dni_trabajador, nombre_trabajador, apellido_trabajador,  horas_fichadas_trabajador from trabajador";

        DbConnection conn = new DbConnection();
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                trabajadores.add(
                        new Worker(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4)));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return trabajadores;
    }

    // ???
    public ObservableList<String> getDniWorkers() {

        ObservableList<String> DNIs = FXCollections.observableArrayList();
        String sql = "select dni_trabajador from trabajador";

        DbConnection conn = new DbConnection();

        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);

            while (resultSet.next()) {
                DNIs.add((resultSet.getString(1)));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return DNIs;
    }

    public String loadSchedules(String dni) {
        String toReturn = "";
        String sql = "select id_horario, hora_inicio_horario, hora_final_horario, tiempo_descanso_horario from horario where id_horario = (select id_horario from horario_trabajador where id_trabajador = (select id_trabajador from trabajador where dni_trabajador = \""
                + dni + "\"))";
        DbConnection conn = new DbConnection();
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                toReturn += resultSet.getString(1)
                        + ": de " + resultSet.getString(2)
                        + " a " + resultSet.getString(3)
                        + ", descanso: " + resultSet.getString(4) + ";";
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return "";
        }
        return toReturn;
    }

    private int getAvailableSignTimeId() {
        String sql = "select id_fichaje from fichaje order by id_fichaje asc";
        DbConnection conn = new DbConnection();
        String toArray = "";
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                toArray += resultSet.getString(1) + ";";
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (toArray == "")
            return 0;
        String[] array = toArray.split(";");
        int id = Integer.parseInt(array[array.length - 1]) + 1;
        return id;
    }

    public void signTime(String dni, int id_horario, double time) {
        String sql = "select id_horario_trabajador from horario_trabajador where id_trabajador = (select id_trabajador from trabajador where dni_trabajador = '"
                + dni + "') and id_horario = " + id_horario;
        DbConnection conn = new DbConnection();
        int id_horario_trabajador = 0;
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next())
                id_horario_trabajador = resultSet.getInt(1);
            sql = "insert into fichaje values (" + this.getAvailableSignTimeId() + ", "
                    + String.valueOf(id_horario_trabajador) + ", " + time + ")";
            try {
                conn.getConn().createStatement().execute(sql);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
