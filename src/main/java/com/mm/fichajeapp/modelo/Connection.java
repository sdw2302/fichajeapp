package com.mm.fichajeapp.modelo;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    // bd
    private final String URL = "jdbc:mysql://localhost/bd_fichaje";//nom bd
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String USER = "administrador";
    private final String PASSWD = "administrador";
    

    public java.sql.Connection connect() {
        java.sql.Connection connection = null;
        try {
            Class.forName(DRIVER); 
            connection = DriverManager.getConnection(URL, USER, PASSWD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }    
        return connection;
    }

    public String getURL() {
        return this.URL;
    }


    public String getDRIVER() {
        return this.DRIVER;
    }


    public String getUSER() {
        return this.USER;
    }


    public String getPASSWD() {
        return this.PASSWD;
    }


}
