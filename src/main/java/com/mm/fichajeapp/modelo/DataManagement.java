package com.mm.fichajeapp.modelo;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManagement {

    ///////////////////////// SECONDARY CONTROLLER (FICHAJE) /////////////////////////

    public ObservableList<Worker> getTableWorkersAsList() { // get a list of all workers

        ObservableList<Worker> trabajadores = FXCollections.observableArrayList();
        String sql = "select dni_trabajador, nombre_trabajador, apellido_trabajador,  horas_fichadas_trabajador from trabajador";

        DbConnection conn = new DbConnection();
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql); // sends query from above to mysql server
            while (resultSet.next()) { // for each result received creates a worker and adds to list
                trabajadores.add(
                        new Worker(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4)));
            }

        } catch (Exception e) { // in case of error
            System.out.println("Error: " + e.getMessage());
        }
        return trabajadores; // returns all workers received as list of objects
    }



    public String loadSchedules(String dni) { // get all assigned schedules of a specific worker
        String toReturn = "";
        String sql = "select id_horario, hora_inicio_horario, hora_final_horario, tiempo_descanso_horario from horario where id_horario in (select id_horario from horario_trabajador where id_trabajador = (select id_trabajador from trabajador where dni_trabajador = \""
                + dni + "\"))";
        DbConnection conn = new DbConnection();
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql); // sends query from above to mysql server
            while (resultSet.next()) { // for each result received adds Strings with schedule info to be divided
                toReturn += resultSet.getString(1)
                        + ": de " + resultSet.getString(2)
                        + " a " + resultSet.getString(3)
                        + ", descanso: " + resultSet.getString(4) + ";";
            }
        } catch (Exception e) { // in case of error
            System.out.println("Error: " + e.getMessage());
            return "";
        }
        return toReturn; // returns all schedules as one String with ; separator
    }

    private int getAvailableSignTimeId() { // generates an id to be used in an insert
        String sql = "select id_fichaje from fichaje order by id_fichaje asc";
        DbConnection conn = new DbConnection();
        String toArray = "";
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql); // sends query from above to mysql server
            while (resultSet.next()) { // for each result received adds String with all ids
                toArray += resultSet.getString(1) + ";";
            }
        } catch (Exception e) { // in case of error
            System.out.println("Error: " + e.getMessage());
        }
        if (toArray == "") // if no ids returned
            return 1;
        String[] array = toArray.split(";"); // divides the ids into an array
        int id = Integer.parseInt(array[array.length - 1]) + 1; // gets last id and adds 1 to return available id
        return id;
    }

    public boolean signTime(String dni, int id_horario, String time) { // inserts data into 'fichaje' table in db
        String sql = "select id_horario_trabajador from horario_trabajador where id_trabajador = (select id_trabajador from trabajador where dni_trabajador = '"
                + dni + "') and id_horario = " + id_horario;
        DbConnection conn = new DbConnection();
        int id_horario_trabajador = 0;
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql); // sends query from above to mysql server
            while (resultSet.next())
                id_horario_trabajador = resultSet.getInt(1); // gets result
            sql = "insert into fichaje values (" + this.getAvailableSignTimeId() + ", "
                    + String.valueOf(id_horario_trabajador) + ", " + time + ")"; // modifies the query
            try {
                conn.getConn().createStatement().execute(sql); // sends query from above to mysql server
            } catch (Exception e) { // in case of error
                System.out.println("Error: " + e.getMessage());
            }
        } catch (Exception e) { // in case of error
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }


    
    ///////////////////////// Schedule Management (Gestion de horarios) /////////////////////////
    public ObservableList<Schedule> getTableSchedulesAsList() {
        ObservableList<Schedule> schedules = FXCollections.observableArrayList();
        // SQL query to retrieve all schedules from the table
        String sql = "select * from horario";
        // Create a database connection
        DbConnection conn = new DbConnection();
        try {
            // Create a statement for executing the SQL query
            Statement ordre = conn.getConn().createStatement();
            // Execute the query and get the result set
            ResultSet resultSet = ordre.executeQuery(sql);
            // Iterate over the result set and create Schedule objects
            while (resultSet.next()) {
                schedules.add(new Schedule(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)));
            }
        } catch (Exception e) { // In case of an error
            System.out.println("Error: " + e.getMessage());
        }
        return schedules;
    }

    public int getAvailableScheduleId() {
        // SQL query to retrieve the available schedule IDs
        String sql = "select id_horario from horario order by id_horario asc";
        // Initialize an empty string to store the IDs
        String toArray = "";
        // Create a database connection
        DbConnection conn = new DbConnection();
        try {
            // Create a statement for executing the SQL query
            Statement ordre = conn.getConn().createStatement();
            // Execute the query and get the result set
            ResultSet resultSet = ordre.executeQuery(sql);
            // Iterate over the result set and append the IDs to the string
            while (resultSet.next()) {
                toArray += resultSet.getString(1) + ";";
            }
        } catch (Exception e) { // In case of an error
            System.out.println("Error: " + e.getMessage());
        }
        // If there are no available IDs, return 0 as the next ID
        if (toArray.equals("")) {
            return 0;
        } else {
            // Split the string of IDs into an array
            String[] array = toArray.split(";");
            // Parse the last ID and add 1 to get the next available ID
            int id = Integer.parseInt(array[array.length - 1]) + 1;
            return id;
        }
    }
    

    public boolean createSchedule(String hora_inicio, String hora_final, String descanso) {
        // Generate the SQL query for inserting a new schedule
        String sql = "insert into horario values (" + getAvailableScheduleId() + ", " + hora_inicio + ", " + hora_final
                + ", " + descanso + ")";
        // Execute the SQL query
        return this.exec(sql);
    }


    public int getAvailableScheduleAssignmentId() {
        // SQL query to retrieve the available schedule assignment IDs
        String sql = "select id_horario_trabajador from horario_trabajador order by id_horario_trabajador asc";
        // Create a database connection
        DbConnection conn = new DbConnection();
        // Initialize an empty string to store the IDs
        String toArray = "";
        try {
            // Create a statement for executing the SQL query
            Statement ordre = conn.getConn().createStatement();
            // Execute the query and get the result set
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                toArray += resultSet.getString(1) + ";";
            }
        } catch (Exception e) { // in case of error
            System.out.println("Error: " + e.getMessage());
        }
        if (toArray.equals("")) {
            return 0;
        }
        String[] array = toArray.split(";");
        int id = Integer.parseInt(array[array.length - 1]) + 1;
        return id;
    }

    public boolean assignSchedule(String dni, int id_horario, int id_dia) {
        // Generate the SQL query for assigning a schedule to a worker
        String sql = "insert into horario_trabajador values (" + this.getAvailableScheduleAssignmentId()
                + ", (select id_trabajador from trabajador where dni_trabajador = '" + dni + "'), " + id_horario + ", "
                + id_dia + ")";
        // Execute the SQL query
        return this.exec(sql);
    }


    ///////////////////////// Create worker (gestion de trabajadores) /////////////////////////

    public ObservableList<Worker> getTableWorkersCompleteAsList() {
        // Get a list of all workers with more data
        ObservableList<Worker> trabajadores = FXCollections.observableArrayList();
        // SQL query to retrieve worker details along with the company name
        String sql = "select dni_trabajador, nombre_trabajador, apellido_trabajador, fecha_nacimiento_trabajador, nombre_empresa from trabajador, empresa where trabajador.id_empresa = empresa.id_empresa;";
    
        DbConnection conn = new DbConnection();
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                // Iterate over the result set and create Worker objects
                trabajadores.add(
                        new Worker(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDate(4).toLocalDate(),
                                resultSet.getString(5)));
            }
    
        } catch (Exception e) { // in case of error
            System.out.println("Error: " + e.getMessage());
        }
        return trabajadores;
    }

    public ObservableList<String> getNameCompanies() {
        // Get a list of names of all companies
        ObservableList<String> empresas = FXCollections.observableArrayList();
        String sql = "select nombre_empresa from empresa;";
    
        DbConnection conn = new DbConnection();
        try {
            Statement ordre = conn.getConn().createStatement();
            ResultSet resultSet = ordre.executeQuery(sql);
            while (resultSet.next()) {
                empresas.add(resultSet.getString(1));
            }
    
        } catch (Exception e) { // in case of error
            System.out.println("Error: " + e.getMessage());
        }
        return empresas;
    }

    // Deletes a worker from the database.
    public boolean deleteWorker(Worker worker) {
        String sql = "delete from trabajador where dni_trabajador = '";
        DbConnection conn = new DbConnection();

        try {
            String dni_trabajador = worker.getDni_trabajador();

            sql += dni_trabajador + "';";

            conn.getConn().createStatement().execute(sql);

        } catch (Exception e) {
            // Print the error message in case of an exception
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    // Creates a new worker in the database.
    public boolean createWorker(String dni, String nombre, String apellido, LocalDate fecha_nacimiento,
                            String empresa_responsable) {
        // SQL query to retrieve the ID of the company
        String sql = "select id_empresa from empresa where nombre_empresa = '" + empresa_responsable
                + "'";
        int id = 0;
        DbConnection conn = new DbConnection();
        try {
            ResultSet res = conn.getConn().createStatement().executeQuery(sql);
            while (res.next())
                id = res.getInt(1);
            try {
                // SQL query to insert the new worker with the provided data
                sql = "insert into trabajador values (" + this.getAvailableWorkers() + ", '" +
                        nombre + "', '" + apellido + "', '" + dni + "', '" + fecha_nacimiento.getYear() + "-"
                        + fecha_nacimiento.getMonthValue() + "-" + fecha_nacimiento.getDayOfMonth() + "', " + id
                        + ", 0.00);";
                conn.getConn().createStatement().execute(sql);
            } catch (Exception e) {
                // Print the error message in case of an exception
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            // Print the error message in case of an exception
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    
    // Retrieves the next available worker ID in the database.
    public int getAvailableWorkers() {
        String sql = "select id_trabajador from trabajador order by id_trabajador asc";
        // Initialize an empty string to store the IDs
        String toArray = "";
        // Create a database connection
        DbConnection conn = new DbConnection();
        try {
            // Create a statement for executing the SQL query
            Statement ordre = conn.getConn().createStatement();
            // Execute the query and get the result set
            ResultSet resultSet = ordre.executeQuery(sql);
            // Iterate over the result set and append the IDs to the string
            while (resultSet.next())
                toArray += resultSet.getString(1) + ";";
        } catch (Exception e) { // In case of an error
            // Print the error message in case of an exception
            System.out.println("Error: " + e.getMessage());
        }
        // If there are no available IDs, return 0 as the next ID
        if (toArray.equals("")) {
            return 0;
        } else {
            // Split the string of IDs into an array
            String[] array = toArray.split(";");
            // Parse the last ID and add 1 to get the next available ID
            int id = Integer.parseInt(array[array.length - 1]) + 1;
            return id;
        }
    }

    // Modifies a worker's information in the database.
    public boolean modifyWorker(String name, String surname, String new_id, LocalDate birthday, String company,
                                String actual_id) {
        // SQL query to update the worker's information
        String sql = "update trabajador set nombre_trabajador = '" + name + "', apellido_trabajador = '" + surname
                + "', dni_trabajador = '" + new_id + "', fecha_nacimiento_trabajador = '" + birthday.getYear() + "-"
                + birthday.getMonthValue() + "-" + birthday.getDayOfMonth()
                + "', id_empresa = (select id_empresa from empresa where nombre_empresa = '" + company
                + "') where dni_trabajador = '" + actual_id + "';";
        // Execute the SQL query using the "exec" method
        return this.exec(sql);
    }

    // Executes the provided SQL query.
    private boolean exec(String sql) {
        // Create a database connection
        DbConnection conn = new DbConnection();
        try {
            // Execute the SQL query using a statement and the database connection
            conn.getConn().createStatement().execute(sql);
        } catch (Exception e) {
            // Print the error message in case of an exception
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }
}
