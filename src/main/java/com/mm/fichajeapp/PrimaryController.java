package com.mm.fichajeapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.sql.SQLException;

import com.mm.fichajeapp.modelo.DbConnection;

import java.sql.Connection;

public class PrimaryController {

    @FXML
    TextField connectionTxt;

    @FXML
    TextField user;

    @FXML
    TextField password;

    @FXML
    Button login;

    DbConnection connection = new DbConnection();

    @FXML
    // private void initialize() {
    // Connection connection = new Connection();
    // java.sql.Connection con = connection.connect();
    // if (con != null) {
    // // Muestra un mensaje de conexión exitosa

    // connectionTxt.setText("Conexión exitosa");
    // System.out.println("XDD");
    // }
    // }

    // public void login() throws IOException {

    // if(user.getText().equals("administrador") &&
    // password.getText().equals("administrador")) {

    // App.setRoot("secondary");
    // }else{
    // Alert alert = new Alert(AlertType.ERROR);
    // alert.setTitle("Error de inicio de sesión");
    // alert.setHeaderText(null);
    // alert.setContentText("El usuario o contraseña son incorrectos.");
    // alert.showAndWait();
    // }

    // }

    public void login() throws SQLException, IOException {
        if (!user.getText().equals("") && !password.getText().equals("")) {
            DbConnection conn = new DbConnection();
            conn.iniciarSesion(user.getText(), password.getText());

            if (conn.getConnection()) {
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Inici de sessió correcte");
                alerta.setHeaderText("Benvingut " + user.getText());
                alerta.setContentText("Que tinguis un bon dia!.");
                alerta.showAndWait();
                App.setRoot("secondary");

            } else {
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Inici de sessió Incorrecte");
                alerta.setHeaderText("No s'ha pogut iniciar sessió");
                alerta.setContentText("Prova introduïnt un usuari i una contrasenya correcta.");
                alerta.showAndWait();
                user.setText("");
                password.setText("");
            }
        } else {
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Inicio de sessión Incorrecto");
            alerta.setHeaderText("No se ha podido iniciar sessión");
            alerta.setContentText("No has introducido un usuario o contraseña.");
            alerta.showAndWait();
        }
    }

}
