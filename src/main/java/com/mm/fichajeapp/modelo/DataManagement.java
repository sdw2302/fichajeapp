package com.mm.fichajeapp.modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManagement {

    public ObservableList<Worker> getTableWorkersAsList() {

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

    public String loadSchedules(String dni) {
        String toReturn = "";
        String sql = "select id_horario, hora_inicio_horario, hora_final_horario, tiempo_descanso_horario from horario where id_horario in (select id_horario from horario_trabajador where id_trabajador = (select id_trabajador from trabajador where dni_trabajador = \""
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

    public boolean signTime(String dni, int id_horario, String time) {
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
            return false;
        }
        return true;
    }

    public ObservableList<Worker> getTableWorkersCompleteAsList() {

        ObservableList<Worker> trabajadores = FXCollections.observableArrayList();
        String sql = "select dni_trabajador, nombre_trabajador, apellido_trabajador, fecha_nacimiento_trabajador, nombre_empresa from trabajador, empresa where trabajador.id_empresa = empresa.id_empresa;";

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
                                resultSet.getDate(4).toLocalDate(),
                                resultSet.getString(5)));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return trabajadores;
    }

    public ObservableList<String> getNameCompanies() {

        ObservableList<String> empresas = FXCollections.observableArrayList();
        String sql = "SELECT nombre_empresa from empresa;";

        DbConnection conn = new DbConnection();
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                empresas.add(
                        resultSet.getString(1));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return empresas;
    }

    public boolean deleteWorker(Worker worker) {
        String sql = "DELETE FROM trabajador WHERE dni_trabajador = '";
        DbConnection conn = new DbConnection();

        try {

            String dni_trabajador = worker.getDni_trabajador();

            sql += dni_trabajador + "';";

            conn.getConn().createStatement().execute(sql);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean createWorker(String dni, String nombre, String apellido, LocalDate fecha_nacimiento,
            String empresa_responsable) {
        String sql = "select id_empresa from empresa where nombre_empresa = '" + empresa_responsable
                + "'";
        int id = 0;
        DbConnection conn = new DbConnection();
        try {
            ResultSet res = conn.getConn().createStatement().executeQuery(sql);
            while (res.next())
                id = res.getInt(1);
            try {
                sql = "insert into trabajador values (" + this.getAvailableWorkers() + ", '" +
                        nombre + "', '" + apellido + "', '" + dni + "', '" + fecha_nacimiento.getYear() + "-"
                        + fecha_nacimiento.getMonthValue() + "-" + fecha_nacimiento.getDayOfMonth() + "', " + id
                        + ", null);";
                conn.getConn().createStatement().execute(sql);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public int getAvailableWorkers() {
        String sql = "select id_trabajador from trabajador order by id_trabajador asc", toArray = "";

        DbConnection conn = new DbConnection();

        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next())
                toArray += resultSet.getString(1) + ";";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (toArray == "")
            return 0;
        String[] array = toArray.split(";");
        int id = Integer.parseInt(array[array.length - 1]) + 1;
        return id;
    }

    public boolean checkDni(String DniToCheck) {

        String DNIs = "";
        String sql = "select dni_trabajador from trabajador";

        DbConnection conn = new DbConnection();

        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);

            while (resultSet.next()) {
                DNIs += ((resultSet.getString(1) + ";"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        String[] dnisArray = DNIs.split(";");

        for (String dni : dnisArray) {
            if (dni.equals(DniToCheck))
                return false;
        }

        return true;
    }

    public ObservableList<Schedule> getTableSchedulesAsList() {
        ObservableList<Schedule> schedules = FXCollections.observableArrayList();

        String sql = "select * from horario";

        DbConnection conn = new DbConnection();

        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                schedules.add(
                        new Schedule(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4)));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return schedules;
    }

    public int getAvailableScheduleId() {
        String sql = "select id_horario from horario order by id_horario asc", toArray = "";

        DbConnection conn = new DbConnection();

        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next())
                toArray += resultSet.getString(1) + ";";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (toArray == "")
            return 0;
        String[] array = toArray.split(";");
        int id = Integer.parseInt(array[array.length - 1]) + 1;
        return id;
    }

    public boolean createSchedule(String hora_inicio, String hora_final, String descanso) {
        String sql = "insert into horario values (" + getAvailableScheduleId() + ", " + hora_inicio + ", " + hora_final
                + ", " + descanso + ")";
        DbConnection conn = new DbConnection();

        try {
            conn.getConn().createStatement().execute(sql);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public int getAvailableScheduleAssignmentId() {
        String sql = "select id_horario_trabajador from horario_trabajador order by id_horario_trabajador asc",
                toArray = "";

        DbConnection conn = new DbConnection();

        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next())
                toArray += resultSet.getString(1) + ";";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (toArray == "")
            return 0;
        String[] array = toArray.split(";");
        int id = Integer.parseInt(array[array.length - 1]) + 1;
        return id;
    }

    public boolean assignSchedule(String dni, int id_horario, int id_dia) {
        String sql = "insert into horario_trabajador values (" + this.getAvailableScheduleAssignmentId()
                + ", (select id_trabajador from trabajador where dni_trabajador = '" + dni + "'), " + id_horario + ", "
                + id_dia + ")";
        DbConnection conn = new DbConnection();

        try {
            conn.getConn().createStatement().execute(sql);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }
}
