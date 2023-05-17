package com.mm.fichajeapp.modelo;

import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DbConnection {

    private static final String URL = "jdbc:mysql://localhost/bd_fichaje"; // URL of the MySQL database
    private static java.sql.Connection conn = null; // Connection object
    Alert alerta = new Alert(AlertType.INFORMATION); // Alert object for displaying information
    private boolean connected = false; // Flag indicating if the connection is established

    // Method to establish a database connection
    public void iniciarSesion(String USER, String PASSWORD) {
        boolean error = false; // Flag to track if there was an error during connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver
            conn = DriverManager.getConnection(URL, USER, PASSWORD); // Establish the connection
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            error = true;
        }
        connected = !error; // Update the connected flag based on the error status
    }

    // Method to check if the database connection is established
    public boolean getConnection() {
        return connected; // Return the connected flag
    }

    // Method to get the database connection object
    public java.sql.Connection getConn() {
        return DbConnection.conn; // Return the connection object
    }
}

