package com.mm.fichajeapp.modelo;

import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DbConnection {

    private static final String URL = "jdbc:mysql://localhost/bd_fichaje";
    private static java.sql.Connection conn = null;
    Alert alerta = new Alert(AlertType.INFORMATION);
    private boolean connected = false;

    public void iniciarSesion(String USER, String PASSWORD) {
        boolean error = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            error = true;
        }
        connected = error == false ? true : false;
    }

    public boolean getConnection() {
        return connected;
    }

    public java.sql.Connection getConn() {
        return this.conn;
    }
}
