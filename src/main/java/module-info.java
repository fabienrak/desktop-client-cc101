module org.app.combatclub101 {
    requires transitive javafx.controls;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive java.desktop;
    requires javafx.fxml;
    requires javafx.swing;
    requires org.xerial.sqlitejdbc;
    requires org.controlsfx.controls;
    requires java.sql;


    opens org.app to javafx.fxml, javafx.graphics, java.base;
    opens org.app.controller to javafx.fxml, javafx.base;
    opens org.app.model to javafx.base;
    exports org.app;

}