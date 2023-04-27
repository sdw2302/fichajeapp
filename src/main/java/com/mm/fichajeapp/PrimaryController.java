package com.mm.fichajeapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

}
