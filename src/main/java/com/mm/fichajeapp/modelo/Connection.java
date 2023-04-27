package com.mm.fichajeapp.modelo;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    // bd
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public java.sql.Connection connect() {
        java.sql.Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost/bd_fichaje?user=administrador&password=administrador");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
