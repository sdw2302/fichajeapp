package com.mm.fichajeapp.modelo;

//import java.sql.DbConnection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DbConnection {
    // bd
    // private final String URL = "jdbc:mysql://localhost/bd_fichaje";//nom bd
    // private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // private final String USER = "administrador";
    // private final String PASSWORD = "administrador";

    private static final String URL = "jdbc:mysql://localhost/bd_fichaje";
    private static java.sql.Connection conn = null;
    Alert alerta = new Alert(AlertType.INFORMATION);
    private boolean connected = false;

    // public java.sql.DbConnection connect() {
    // java.sql.DbConnection connection = null;
    // try {
    // Class.forName(DRIVER);
    // connection = DriverManager.getConnection(URL, USER, PASSWD);
    // } catch (SQLException | ClassNotFoundException e) {
    // System.out.println(e.getMessage());
    // }
    // return connection;
    // }

    public void iniciarSesion(String USER, String PASSWORD) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            connected = false;
        }
        connected = true;
    }

    public boolean getConnection() {
        return connected;
    }
}
