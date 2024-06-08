module org.example.idproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens org.example.idproject.common to javafx.base;
    exports org.example.idproject;


    opens org.example.idproject.view to javafx.fxml;
    opens org.example.idproject.view.browsingScreens to javafx.fxml;
    opens org.example.idproject.view.insertDataScreens to javafx.fxml;
    opens org.example.idproject.view.infoPanes to javafx.fxml;
    opens org.example.idproject.view.dataTables to javafx.fxml;

    exports org.example.idproject.view;
    exports org.example.idproject.viewmodel;
    exports org.example.idproject.common;
    exports org.example.idproject.core;
}