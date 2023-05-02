package com.mm.fichajeapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import com.mm.fichajeapp.modelo.Connection;

public class PrimaryController {

    @FXML
    TextField connectionTxt;

    @FXML
    TextField user;

    @FXML
    TextField password;

    @FXML
    Button login;

    @FXML
    private void initialize() {
        Connection connection = new Connection();
        java.sql.Connection con = connection.connect();
        if (con != null) {
            // Muestra un mensaje de conexión exitosa
            
            connectionTxt.setText("Conexión exitosa");
            System.out.println("XDD");
        }
    }

    public void login() throws IOException {

        if(user.getText().equals("administrador") && password.getText().equals("administrador")) {
            
                App.setRoot("secondary");
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText(null);
            alert.setContentText("El usuario o contraseña son incorrectos.");
            alert.showAndWait();
        }

    }

}
