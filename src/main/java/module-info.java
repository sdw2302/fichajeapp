module com.mm.fichajeapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.swing;
    requires java.desktop;
    requires javafx.base;

    opens com.mm.fichajeapp to javafx.fxml;

    exports com.mm.fichajeapp;
}
