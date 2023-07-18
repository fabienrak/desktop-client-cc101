module org.app.combatclub101 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.xerial.sqlitejdbc;
    requires org.controlsfx.controls;
    requires java.sql;

    opens org.app to javafx.fxml, javafx.graphics;
    opens org.app.controller to javafx.fxml;
    opens org.app.model to javafx.base;
    exports org.app;

}