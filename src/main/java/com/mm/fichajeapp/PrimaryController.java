package com.mm.fichajeapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.sql.SQLException;

import com.mm.fichajeapp.modelo.DbConnection;

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
    public void login() throws SQLException, IOException {
        //user.setText("administrador");
        //password.setText("administrador");
        
        if (!user.getText().equals("") && !password.getText().equals("")) {
            DbConnection conn = new DbConnection(); // Create a new database connection
            conn.iniciarSesion(user.getText(), password.getText()); // Attempt to establish a connection

            if (conn.getConnection()) {
                App.setRoot("secondary"); // If the connection is successful, switch to the secondary view

            } else {
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Inici de sessió Incorrecte");
                alerta.setHeaderText("No s'ha pogut iniciar sessió");
                alerta.setContentText("Prova introduïnt un usuari i una contrasenya correcta.");
                alerta.showAndWait();
                user.setText(""); // Clear the username field
                password.setText(""); // Clear the password field
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



