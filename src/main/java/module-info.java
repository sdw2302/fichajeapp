module com.mm.fichajeapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mm.fichajeapp to javafx.fxml;
    exports com.mm.fichajeapp;
}
